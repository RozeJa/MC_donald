import { useContext } from 'react'
import './ProductInOrder.css'
import OrderProvider from '../OrderProvider'

// data obsahují:   product
const ProductInOrder = (data) => {
    
    const OrderP = useContext(OrderProvider)
    const product = data.product

    return (
        <div className='product-in-order'>
            <p>{product.product.name} {countPrice(product)} Kč</p>
            <div className='product-in-order-controll'>
                <input type='number' defaultValue={product.count} />
                <button onClick={() => {
                    OrderP.setOrder({ ...OrderP.order, ['products']: OrderP.order.products.filter(p => p.product.id !== product.product.id) })
                }}>X</button>                
            </div>

        </div>
    )
}

export default ProductInOrder

function countPrice(product) {
    let price = product.count * product.product.price
    
    product.improvements.forEach(improvement => {
        price += improvement.price
    });

    return price
}
