import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import './Product.css'
import ImprovementInProduct from '../components/ImprovementInProduct'
import { Input } from 'reactstrap'

const Product = () => {    
    const initProduct = {
        name: '',
        price: '',
        availableImprovements: null
    }

    const navigate = useNavigate()
    const { productId } = useParams()
    const [count, setCount] = useState(1)
    const [product, setProduct] = useState(initProduct)

    const [improvements, setImprovements] = useState([])
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
        if (product.availableImprovements !== null) {
            setImprovementsShow(Object.values(product.availableImprovements).map(improvement => <ImprovementInProduct key={improvement.id} improvement={improvement} handleChange={(e) => {

                let isChecked = e.target.checked
                let improvementId = e.target.name

                if (isChecked) {
                    let imps = improvements
                    imps.push(product.availableImprovements[improvementId])
                    setImprovements(imps)
                } else {
                    setImprovements(improvements.filter(improvement => improvement.id !== improvementId))
                }
            }} />))
        } 
        //setImprovements(product.availableImprovements.values().map(improvement => {}))
    }, [product])

    const handleChange = (e) => {
        const { value } = e.target
    
        setCount(Math.min(30, Math.max(0, parseInt(value))))
    }

    const order = () => {

        let order = JSON.parse(sessionStorage.getItem('order'))

        let productInOrder = order.products.find(p => compare(p, product, improvements))

        if (productInOrder === undefined) {
            order.products.push({
                count: count,
                product: {
                    id: product.id,
                    name: product.name,
                    price: product.price,
                },
                improvements: improvements
            })
            sessionStorage.setItem('order', JSON.stringify(order))
        } else {
            order.products = order.products.map(p => {
                if (compare(p, product, improvements))
                    p.count += count
                return p
            })
            sessionStorage.setItem('order', JSON.stringify(order))
        }

        console.log(JSON.parse(sessionStorage.getItem('order')))

        navigate('/order/categories')
    }

    return (
        <div className='product'>
            <div className='product-product'>
                <h1>{product.name} {product.price} Kč</h1>
                <Input type='number' min='1' max='30' value={count} onChange={handleChange} />
            </div>
            <div className='product-improvements'>
                {improvementsShow}
            </div>
            <div className='product-btns'>
                <button onClick={() => {navigate('/order/categories')}}>Cancel</button>
                <button onClick={order}>Order</button>
            </div>
        </div>
    )
}

export default Product

function compare(p, product, improvements) {
    let sameProduct = p.product.id === product.id 
    let sameImprovements = p.improvements.filter(improvement => {
        
        return improvements.find(i => i.id === improvement.id) === undefined  
    }) === []

    return sameProduct && sameImprovements || p.improvements.length === 0 && sameProduct
}