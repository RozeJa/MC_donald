import { useEffect, useState } from 'react'
import './CategoryPanel.css'
import OrderCategory from '../components/OrderCategory'

const CategoryPanel = () => {

    const [categories, setCategories] = useState([])

    useEffect(() => {
        fetch('http://localhost:8080/api/categories/')
            .then(res => {
                if (res.status === 200) {
                    return res.json()
                } else {
                    return []
                }
            })
            .then(data => {
                setCategories(data.map(c => <OrderCategory key={c.id} category={c} />))
            })
    }, [])

    return (
        <div className='category-panel'>
            <div className='category-panel-categories'>
                {categories}
            </div>
            <div className='category-panel-btns'>
                <a href='/order/completeOrder'>Dokončit objednávku</a>
            </div>
        </div>
    )
}

export default CategoryPanel