package Main

import (
	"math/rand"
	"sync"
	"time"
)

type Semaphore chan int

func (semaphore Semaphore) release(n int) {
	element := 0
	for i := 0; i < n; i++ {
		semaphore <- element
	}
}

func (semaphore Semaphore) acquire(n int) {
	for i := 0; i < n; i++ {
		<-semaphore
	}
}

const (
	TOBACCO = "tobacco"
	PAPER   = "paper"
	MATCHES = "matches"
)

type Smoker struct {
	id         int
	item       string
	semaphore  *Semaphore
	tableItems *chan Table
}

type Manager struct {
	tableItems       *chan Table
	smokerSemaphores [3]*Semaphore
}

type Table struct {
	item1 string
	item2 string
}

func smokerExecutor(smoker Smoker, wg *sync.WaitGroup) {
	for true {
		smoker.semaphore.acquire(1)
		time.Sleep(850 * time.Millisecond)

		items := <-*smoker.tableItems
		if items.item1 != smoker.item && items.item2 != smoker.item {
			println("Smoker #", smoker.id, "(", smoker.item, ")", "CREATED and now is SMOKING a cigarette")
		} else {
			println("Smoker #", smoker.id, "(", smoker.item, ")", "does not have suitable item")
		}

		wg.Done()
		*smoker.tableItems <- items
	}
}

func managerExecutor(manager *Manager, wg *sync.WaitGroup) {
	for true {
		// wait until next round
		wg.Wait()
		time.Sleep(500 * time.Millisecond)

		newTableItems := createRandomTableItems()
		println("\nManager is putting items on the table:", newTableItems.item1, ",", newTableItems.item2)

		if len(*manager.tableItems) != 0 {
			<-*manager.tableItems
		}
		*manager.tableItems <- newTableItems

		for _, semaphore := range manager.smokerSemaphores {
			wg.Add(1)
			semaphore.release(1)
		}
	}
}

func createRandomTableItems() Table {
	rand.Seed(time.Now().UTC().UnixNano())
	table := Table{}

	switch rand.Intn(3) {
	case 0:
		table.item1 = TOBACCO
		table.item2 = PAPER
	case 1:
		table.item1 = TOBACCO
		table.item2 = MATCHES
	case 2:
		table.item1 = PAPER
		table.item2 = MATCHES
	}
	return table
}

func Main() {
	smokerSemap1 := make(Semaphore, 1)
	smokerSemap2 := make(Semaphore, 1)
	smokerSemap3 := make(Semaphore, 1)
	smokerSemaphores := [3]*Semaphore{&smokerSemap1, &smokerSemap2, &smokerSemap3}

	tableItems := make(chan Table, 1)
	smokers := [...]Smoker{
		{1, TOBACCO, smokerSemaphores[0], &tableItems},
		{2, PAPER, smokerSemaphores[1], &tableItems},
		{3, MATCHES, smokerSemaphores[2], &tableItems},
	}
	manager := Manager{&tableItems, smokerSemaphores}

	var wg sync.WaitGroup

	go managerExecutor(&manager, &wg)
	for _, smoker := range smokers {
		go smokerExecutor(smoker, &wg)
	}

	time.Sleep(60 * time.Minute)
}
