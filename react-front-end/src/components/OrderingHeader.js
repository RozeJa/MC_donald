import { useEffect, useState } from 'react'
import './OrderingHeader.css'

// data obsahují:   categories
//                  setSelectedCategory
const OrderingHeader = (data) => {

    const [showCategories, setShowCategories] = useState([])

    useEffect(() => {
        setShowCategories(data.categories.map(category => {
            return  <div className='category-in-header' key={category.id} onClick={() => data.setSelectedCategory(category)}>
                        <h2>{category.name}</h2>
                    </div>
        }))
    }, [data.categories])

    return (
        <div className='order-header'>
            {showCategories}
        </div>
    )
}

export default OrderingHeader