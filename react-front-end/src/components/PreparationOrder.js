import { useEffect, useState } from 'react'
import './PreparationOrder.css'
import PreparationProduct from './PreparationProduct'

const PreparationOrder = (data) => {

    const order = data.order
    const [products, setProducts] = useState([])

    useEffect(() => {
        setProducts(order.products.map(p => {
            return (
                <PreparationProduct key={p.product.id} product={p} />
            )
        }))
    }, [])

    return (
        <div className='preparation-order' onClick={() => {
            order.finished = true
            fetch('http://localhost:8080/api/orders/' + order.id, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(order)
            }).then(response => {
                if (response.status === 200) {
                    console.log("odebrání se podařilo");
                }
            })
        }}>
            <h3>Order: {order.number}</h3>
            <div>
                <h4>Products: </h4>
                {products}
            </div>
        </div>
    )
}

export default PreparationOrder