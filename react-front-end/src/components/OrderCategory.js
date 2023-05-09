import './OrderCategory.css'

const OrderCategory = (props) => {
    return (
        <a href={'/order/products/' + props.category.id} >{props.category.name}</a>
    )
}

export default OrderCategory