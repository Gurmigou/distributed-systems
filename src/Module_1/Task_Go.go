package Main

import (
	"container/list"
	"math/rand"
	"sync"
	"time"
)

func phoneToOperator(customer int, clientsQueue *list.List, lock *sync.Mutex) {
	for true {
		lock.Lock()
		println("#", customer, " Entered the queue")
		clientsQueue.PushBack(customer)
		lock.Unlock()

		rand.Seed(time.Now().UnixNano())
		randNum := rand.Intn(2900) + 100
		time.Sleep(time.Duration(randNum) * time.Millisecond)

		lock.Lock()
		found := findElementInList(clientsQueue, customer)
		if found {
			println("Customer #", customer, " leaved queue")
			deleteElementFromList(clientsQueue, customer)
			lock.Unlock()

			time.Sleep(time.Duration(randNum/4) * time.Millisecond)

		} else {
			lock.Unlock()
			wg.Done()
			break
		}

	}
}

func findElementInList(list *list.List, element int) bool {
	for e := list.Front(); e != nil; e = e.Next() {
		if e.Value == element {
			return true
		}
	}
	return false
}

func deleteElementFromList(list *list.List, element int) {
	for e := list.Front(); e != nil; e = e.Next() {
		if e.Value == element {
			list.Remove(e)
		}
	}
}

func handleCustomer(operator int, clientsQueue *list.List, lock *sync.Mutex) {
	for true {
		lock.Lock()
		if clientsQueue.Len() > 0 {

			num := clientsQueue.Front().Value.(int)

			println("Operator #", operator, "  is served by customer #", num)

			// remove first element from clientsQueue
			front := clientsQueue.Front()
			clientsQueue.Remove(front)

		}
		lock.Unlock()

		rand.Seed(time.Now().UnixNano())
		randNum := rand.Intn(2000)

		time.Sleep(time.Duration(randNum) * time.Millisecond)
	}
}

var wg sync.WaitGroup

func Main() {
	var clientsQueue = list.New()
	var lock sync.Mutex

	for i := 0; i < 10; i++ {
		wg.Add(1)
		go phoneToOperator(i, clientsQueue, &lock)
	}

	for i := 0; i < 2; i++ {
		go handleCustomer(i, clientsQueue, &lock)
	}

	wg.Wait()
}
