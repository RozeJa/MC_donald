import { useContext, useEffect, useState } from 'react'
import './Order.css'
import ProductInOrder from './ProductInOrder'
import OrderProvider from '../OrderProvider'

const Order = () => {

    const OrderP = useContext(OrderProvider)
    const [products, setProducts] = useState([])
    const [price, setPrice] = useState(0);

    useEffect(() => {
        setProducts(OrderP.order.products.map(product => {
            return  <ProductInOrder key={Math.random()} product={product} />
        }))
        setPrice(countPrice(OrderP.order))
    }, [OrderP.order])

    return (
        <div className='order'>
            <div className="header-distance"></div>
            
            <div className='products'>
                <h3>Order: </h3>
                {products}
            </div>

            <div className='order-price'><h2>Cena celkem: {price} Kč</h2></div>
            
            <div className='order-btns' > 
                <button onClick={() => OrderP.setOrder(OrderP.initOrder)}>Cancel</button>
                <button onClick={() => confirmOrder(OrderP)}>Buy</button>
            </div>
        </div>
    )
}

export default Order

function confirmOrder(OrderP) {
    fetch('http://localhost:8080/api/orders/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(OrderP.order)
    })
    .then(response => {
        if (response.status === 200) {
            OrderP.setOrder(OrderP.initOrder)
        }
    })
}

function countPrice(order) {
    let price = 0

    order.products.forEach(p => {
        price += p.product.price * p.count 
        p.improvements.forEach(improvement => {
            price += improvement.price * p.count
        })
    })

    return price
}