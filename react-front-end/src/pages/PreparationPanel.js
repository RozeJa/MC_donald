import { useEffect, useState } from 'react'
import './PreparationPanel.css'
import PreparationOrder from '../components/PreparationOrder'
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

let stompClient
const PreparationPanel = () => {

    let [orders, setOrders] = useState([])
    const [refresh, setRefresh] = useState(false)
    const [showedOrdesr, setShowedOrders] = useState(<></>)

    useEffect(() => {
        let sockJS = new SockJS('http://localhost:8080/ws')
    
        stompClient = Stomp.over(sockJS)
        stompClient.connect({}, onConnected)
    }, [])

    const onConnected = () => {
        stompClient.subscribe('/orders/distributeOrder', onReseaveOrder)
    }
    const onReseaveOrder = (order) => {

        console.log(orders);
        const parsedOrder = JSON.parse(order.body)

        let isIn = orders.find(order => order.id === parsedOrder.id) !== undefined

        if (!parsedOrder.finished && !isIn) {
            orders.push(parsedOrder)
            orders = orders
            setRefresh(!refresh)
        } else if (parsedOrder.finished) {

            orders = orders.map(a=>a)
            setRefresh(!refresh)
        }
    }

    useEffect(() => {
        if (refresh) 
            setShowedOrders(orders.filter(order => !order.finished).map(order => <PreparationOrder key={order.id} order={order} />)) // TODO
        setRefresh(false)
    }, [refresh])

    return (
        <div className='preparation-panel'>
            {showedOrdesr}
        </div>
    )
}

export default PreparationPanel