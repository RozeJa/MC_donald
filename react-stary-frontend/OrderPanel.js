import './OrderPanel.css'
import Order from '../components/Order'
import OrderingPanel from '../components/OrderingPanel'

const OrderPanel = () => {

    return (
        <div className='order-panel'>
            <OrderingPanel />
            <Order />
        </div>
    )
}

export default OrderPanel