import { useNavigate } from 'react-router-dom'
import './OrderPanel.css'

const OrderPanel = () => {

    const navigate = useNavigate()

    return (
        <div className='order-panel' onClick={() => navigate('/order/categories/')}>
            <div className='order-panel-bg'>
                <p>Objednej</p>
            </div>
        </div>
    )
}

export default OrderPanel