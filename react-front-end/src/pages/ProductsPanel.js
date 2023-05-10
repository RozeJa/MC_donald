import { useParams } from 'react-router-dom';
import './ProductsPanel.css'
import { useEffect, useState } from 'react';
import OrderProduct from '../components/OrderProduct';

const ProductsPanel = () => {

    const { categoryId } = useParams()
    const [products, setProducts] = useState([])

    useEffect(() => {
        fetch(`http://localhost:3000/api/products/`)
            .then(res => {
                if (res.status === 200) {
                    return res.json()
                } else {
                    return {}
                }
            })
            .then(data => {
                let products = data.filter(product => product.category.id === categoryId)
                setProducts(products.map(product => <OrderProduct key={product.id} product={product} />))
            })
    }, [categoryId])

    return (
        <div className='product-panel'>
            <div className='product-panel-products'>
                {products}
            </div>
            <div className='product-panel-btns'>
                <a href='/order/categories'>Výběr kategorie</a>
                <a href='/order/completeOrder'>Dokončit objednávku</a>
            </div>
        </div>
    )
}

export default ProductsPanel