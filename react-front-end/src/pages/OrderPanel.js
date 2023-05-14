import { useNavigate } from 'react-router-dom'
import './OrderPanel.css'

const OrderPanel = () => {

    const navigate = useNavigate()

    return (
        <div className='order-panel' onClick={() => navigate('/order/categories/')}>
            <p>Objednej</p>
        </div>
    )
}

export default OrderPanel