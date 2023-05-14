import { useEffect, useState } from 'react'
import './ProductInOrder.css'
import { Input } from 'reactstrap'
import { useNavigate } from 'react-router-dom'
import compare from '../functions/compare'

const ProductInOrder = (props) => {

    const navigate = useNavigate()
    const product = props.product
    const [improvements, setImprovements] = useState([])

    useEffect(() => {
        setImprovements(product.improvements.map(i => <p key={product.count + 'x' + i.name + i.price} className='product-in-order-improvement'>{product.count}x {i.name} {i.price} Kč</p>))
    }, [])

    const editProduct = () => {
        sessionStorage.setItem('editedProduct', JSON.stringify(product))

        let order = JSON.parse(sessionStorage.getItem('order'))       
        
        order.products = order.products.filter(p => !compare(p, {id:product.product.id}, product.improvements))
        sessionStorage.setItem('order', JSON.stringify(order))

        navigate('/order/addProduct/' + product.product.id)
    }

    const removeProduct = () => {
        let order = JSON.parse(sessionStorage.getItem('order'))
   
        console.log(order)
        console.log(product);
       
        order.products = order.products.filter(p => !compare(p, {id:product.product.id}, product.improvements))

        console.log(order)
    
        sessionStorage.setItem('order', JSON.stringify(order))
        props.setOrder(order)
    }

    return (
        <div className='product-in-order'>
            <div className='product-in-order-header'>
                <h2 onClick={() => editProduct()}>{product.count}x {product.product.name}  {countPrice(product)} Kč</h2>
                <button onClick={() => removeProduct()}>X</button>  
            </div>    
            <div className='product-in-order-improvements'>
                {improvements}
            </div>
        </div>
    )
}

export default ProductInOrder

function countPrice(product) {
    let price = product.product.price

    product.improvements.forEach(i => {
        price += i.price
    })

    return price * product.count
}