import { useNavigate } from 'react-router-dom'
import './OrderPanel.css'

const OrderPanel = () => {

    const navigate = useNavigate()
    

    return (
        <div className='order-panel' onClick={() => navigate('/order/categories/')}>
            <video className='order-panel-video' autoPlay loop muted >
                <source src='/videos/videoSequence.mp4' type="video/mp4" />
            </video>
            <p>Objednej</p>
        </div>
    )
}

export default OrderPanel