import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Input } from 'reactstrap'
import './Product.css'
import ImprovementInProduct from '../components/ImprovementInProduct'
import compare from '../functions/compare'

const Product = () => {
    const initProduct = {
        name: '',
        price: '',
        availableImprovements: null
    }

    const initProductInOrder = {
        product: {
            id: '',
            name: '',
            price: ''
        },
        improvements: [],
        count: 1
    }

    const loaded = sessionStorage.getItem('editedProduct') !== null

    const navigate = useNavigate()
    const { productId } = useParams()
    const [product, setProduct] = useState(initProduct)
    const [productInOrder, setProductInOrder] = useState(loaded ? JSON.parse(sessionStorage.getItem('editedProduct')) : initProductInOrder)

    const [improvementsShow, setImprovementsShow] = useState([])

    useEffect(() => {
        fetch(`http://localhost:8080/api/products/${productId}`)
            .then(res => {
                if (res.status === 200) {
                    return res.json()
                } else {
                    return initProduct
                }
            })
            .then(data => setProduct(data))
    }, [productId])

    useEffect(() => {
        setProductInOrder({ ...productInOrder, ["product"]: product })
        if (product.availableImprovements !== null) {
            setImprovementsShow(Object.values(product.availableImprovements).map(improvement => <ImprovementInProduct key={improvement.id} improvement={improvement} improvements={productInOrder.improvements} handleChange={(e) => {

                let isChecked = e.target.checked
                let improvementId = e.target.name

                if (isChecked) {
                    productInOrder.improvements.push(product.availableImprovements[improvementId])
                } else {
                    productInOrder.improvements = productInOrder.improvements.filter(improvement => improvement.id !== improvementId)
                }
            }} />))
        }
    }, [product])

    const incCount = () =>{
        setProductInOrder({ ...productInOrder, ['count']: Math.min(30, productInOrder.count + 1)})
    }

    const handleChange = (e) => {
        const { value } = e.target

        setProductInOrder({ ...productInOrder, ['count']: Math.min(30, Math.max(0, parseInt(value))) })
    }

    const order = () => {

        let order = JSON.parse(sessionStorage.getItem('order'))

        let pio = order.products.find(p => compare(p, product, productInOrder.improvements))

        if (pio === undefined) {
            order.products.push(productInOrder)
            sessionStorage.setItem('order', JSON.stringify(order))
        } else {
            order.products = order.products.map(p => {
                if (compare(p, product, productInOrder.improvements))
                    p.count += productInOrder.count
                return p
            })
            sessionStorage.setItem('order', JSON.stringify(order))
        }

        console.log(JSON.parse(sessionStorage.getItem('order')))

        sessionStorage.removeItem('editedProduct')

        if (loaded) {
            navigate('/order/completeOrder')
        } else 
            navigate('/order/categories')
    }

    const cancel = () => {
        if (loaded) {

            let order = JSON.parse(sessionStorage.getItem('order'))

            order.products.push(JSON.parse(sessionStorage.getItem(
            'editedProduct')))
            sessionStorage.setItem('order', JSON.stringify(order))

            sessionStorage.removeItem('editedProduct')
            navigate('/order/completeOrder')
        } else {
            sessionStorage.removeItem('editedProduct')
            navigate('/order/categories')            
        }

    }

    return (
        <div className='product'>
            <div hidden key={product.id}></div>
            <div className='product-product' name={JSON.stringify(productInOrder)}>
                <h1 onClick={incCount}>{product.name} {product.price} Kč</h1>
                <Input type='number' min='1' max='30' value={productInOrder.count} onChange={handleChange} />
            </div>
            <div className='product-improvements-header'>
                <h2>Dostupná vylepšení: </h2>
                <div className='product-improvements'>
                    {improvementsShow}
                </div>
            </div>
            <div className='product-btns'>
                <button onClick={cancel}>Cancel</button>
                <button onClick={order}>Order</button>
            </div>
        </div>
    )
}

export default Product