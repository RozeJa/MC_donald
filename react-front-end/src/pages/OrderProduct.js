import { useNavigate } from 'react-router-dom'
import './OrderProduct.css'

const OrderProduct = (props) => {

    const navigate = useNavigate()

    return (
        <div className='order-product' onClick={() => {navigate(`/order/addProduct/${props.product.id}`)}}>
            <h3>{props.product.name}</h3>
            <h4>{props.product.price}</h4>
        </div>
    )
}

export default OrderProduct