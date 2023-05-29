import { useEffect, useState } from 'react'
import './CompleteOrder.css'
import { useNavigate } from 'react-router-dom'
import ProductInOrder from '../components/ProductInOrder'
import Compleating from '../components/Compleating'

const CompleteOrder = () => {

    const navigate = useNavigate()
    const [order, setOrder] = useState(JSON.parse(sessionStorage.getItem('order')))
    const [products, setProducts] = useState([])
    const [compleating, setCompleating] = useState(<></>);

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

        setCompleating(
            <div className='compleat-order-compleating'>
                <Compleating  />
            </div>
        )

        setTimeout(() => {
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
        }, 3000)
    }

    return (
        <div className='complete-order'>
            
            <video className='order-panel-video' autoPlay loop muted >
                <source src='/videos/videoSequence.mp4' type="video/mp4" />
            </video>

            {compleating}

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