import { useEffect, useState } from 'react'
import './DeliveryPanel.css'
import { Stomp } from '@stomp/stompjs'
import DistributionOrder from '../components/DistributionOrder'

const url = "ws://localhost:8080/ws"
const client = Stomp.client(url)

let preparing = []
let finished = []

const DeliveryPanel = () => {
    
    const [refresh, setRefresh] = useState(false)
    const [preparingShow, setPreparinShow] = useState([])
    const [finishedShow, setFinishedShow] = useState([])

    useEffect(() => {
        client.connect({}, () => {
            client.subscribe('/orders/distributeOrder', onReseaveOrder)
        })
    }, [])

    const onReseaveOrder = (order) => {

        const parsedOrder = JSON.parse(order.body)

        let isIn = preparing.find(order => order.id === parsedOrder.id) !== undefined

        if (!parsedOrder.finished && !isIn) {
            preparing.push(parsedOrder)
            setRefresh(!refresh)
        } else if (parsedOrder.finished) {
            preparing = preparing.filter(order => order.id !== parsedOrder.id)
            finished.push(parsedOrder)
            setRefresh(!refresh)
        }
    }

    useEffect(() => {
        if (!refresh) return
        
        setPreparinShow(preparing.map(po => {
            return <DistributionOrder key={po.id} order={po} collback={(order) => {}} />
        })) 

        setFinishedShow(finished.map(po => {
            return <DistributionOrder key={po.id} order={po} collback={(removed) => {

                finished = finished.filter(order => order.id !== removed.id)
                setRefresh(true)
            }} />
        })) 
        setRefresh(false)
    }, [refresh])

    return (
        <div className='delivery-panel'>
            <div className='prepiring-delivery'>
                <h1>V přípravě</h1>
                {preparingShow}
            </div>
            <div className='finished-delivery'>
                <h1>K vyzvednutí</h1>
                {finishedShow}
            </div>
        </div>
    )
}

export default DeliveryPanel