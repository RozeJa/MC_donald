import { useEffect, useState } from 'react'
import './CompleteOrder.css'
import { useNavigate } from 'react-router-dom'
import ProductInOrder from '../components/ProductInOrder'

const CompleteOrder = () => {

    const navigate = useNavigate()
    const [order, setOrder] = useState(JSON.parse(sessionStorage.getItem('order')))
    const [products, setProducts] = useState([])

    useEffect(() => {
        setProducts(order.products.map(product => {
            return <ProductInOrder key={/*getProductId(product)*/ Math.random()} product={product} setOrder={setOrder} />
        }))
    }, [order])

    const cancel = () => {
        sessionStorage.removeItem('order')
        sessionStorage.setItem('order', JSON.stringify({
            products: [],
            finished: false
        }))
        navigate('/order')
    }

    const pay = () => {
        fetch('http://localhost:8080/api/orders/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        })
        .then(res => {
            if (res.status === 200) {
                sessionStorage.removeItem('order')
                sessionStorage.setItem('order', JSON.stringify({
                    products: [],
                    finished: false
                }))
                navigate('/order')
            } else {
                return {}
            }
        })
    }

    return (
        <div className='complete-order'>
            <div className='compleat-order-header'>
                <h1>Vaše objednávka obsahuje:</h1>
            </div>
            <div className='complete-order-products'>
                {products}
            </div>
            <div className='complete-order-btns'>
                <h4 onClick={() => navigate('/order/categories/')}>Pokračovat ve výběru</h4>
                <h4 onClick={cancel}>Zrušit objednávku</h4>
                <h4 onClick={pay}>Zaplatit kartou {countPrice(order)} Kč </h4>
            </div>
        </div>
    )
}

export default CompleteOrder

function countPrice(order) {
    let price = 0

    console.log(order);

    order.products.forEach(product => {
        product.improvements.forEach(i => {
            price += i.price * product.count
        })            
        price += product.product.price * product.count
    })

    return price
}