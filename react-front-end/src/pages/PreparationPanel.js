import { useEffect, useState } from 'react'
import './PreparationPanel.css'
import PreparationOrder from '../components/PreparationOrder'
import { Stomp } from '@stomp/stompjs'


const url = "ws://localhost:8080/ws"
const client = Stomp.client(url)
let orders = []

const PreparationPanel = () => {

    const [refresh, setRefresh] = useState(false)
    const [showedOrdesr, setShowedOrders] = useState(<></>)

    useEffect(() => {
        client.connect({}, () => {
            client.subscribe('/orders/distributeOrder', onReseaveOrder)
        })
    }, [])

    const onReseaveOrder = (order) => {

        const parsedOrder = JSON.parse(order.body)

        let isIn = orders.find(order => order.id === parsedOrder.id) !== undefined

        if (!parsedOrder.finished && !isIn) {
            orders.push(parsedOrder)
            setRefresh(!refresh)
        } else if (parsedOrder.finished) {
            orders = orders.filter(order => order.id !== parsedOrder.id)
            setRefresh(!refresh)
        }
    }

    useEffect(() => {
        if (refresh) 
            setShowedOrders(orders.filter(order => !order.finished).map(order => <PreparationOrder key={order.id} order={order} />))
        setRefresh(false)
    }, [refresh])

    return (
        <div className='preparation-panel'>
            {showedOrdesr}
        </div>
    )
}

export default PreparationPanel