import { useNavigate } from 'react-router-dom'
import './OrderProduct.css'

const OrderProduct = (props) => {

    const navigate = useNavigate()

    const divStyle = {
        backgroundImage: `url(/imgs/product-imgs/${props.product.bgImgURI})`,
    };

    return (
        <div className='order-product' style={divStyle} onClick={() => {navigate(`/order/addProduct/${props.product.id}`)}}>
            <div className='order-product-background_color'>
                <h3>{props.product.name}</h3>
                <h4>{props.product.price} Kč</h4>
            </div>
        </div>
    )
}

export default OrderProduct