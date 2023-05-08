import { useEffect, useState } from 'react'
import './DeliveryPanel.css'

const DeliveryPanel = () => {

    const [preparing, setPreparing] = useState([])
    const [finished, setFinished] = useState([])
    
    const [preparingShow, setPreparinShow] = useState([])
    const [finishedShow, setFinishedShow] = useState([])

    useEffect(() => {
        setPreparinShow() // TODO
    }, [preparing])

    
    useEffect(() => {
        setFinishedShow() // TODO
    }, [finished])

    return (
        <div className='delivery-panel'>
            <div className='prepiring-delivery'>
                {preparingShow}
            </div>
            <div className='finished-delivery'>
                {finishedShow}
            </div>
        </div>
    )
}

export default DeliveryPanel