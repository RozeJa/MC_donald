import { useEffect, useState } from 'react'
import './OrderingPanel.css'
import OrderingHeader from './OrderingHeader'
import OrderingBody from './OrderingBody'

const OrderingPanel = () => {

    const [orderingHeader, setOrderHeader] = useState(<></>)
    const [orderingBody, setOrderingBody] = useState(<></>)

    const [categories, setCategories] = useState([])
    const [products, setProducts] = useState([])
    const [selectedCategory, setSelectedCategory] = useState({id: ''})

    useEffect(() => {
        fetch('http://localhost:8080/api/categories/')
        .then(response => {
            if (response.status === 200) {
                return response.json()
            } else {
                return [];
            }
        })
        .then(categories => {
            if (categories.length > 0)
                setSelectedCategory(categories[0])
            setCategories(categories)
        })
    }, [])

    useEffect(() => {

        fetch('http://localhost:8080/api/products/')
        .then(response => {
            if (response.status === 200) {
                return response.json()
            } else {
                return [];
            }
        })
        .then(products => { 
            setProducts(products.filter(product => product.category.id === selectedCategory.id))
        })   
            
    }, [selectedCategory])

    useEffect(() => {
        setOrderHeader(<OrderingHeader categories={categories} setSelectedCategory={(selectedCategory) => {setSelectedCategory(selectedCategory)}} />)
    }, [categories])

    useEffect(() => {
        setOrderingBody(<OrderingBody products={products} />)
    }, [products])

    return (
        <div className='ordering-panel'>
            {orderingHeader}
            {orderingBody}
        </div>
    )
}

export default OrderingPanel