import './OrderCategory.css'

const OrderCategory = (props) => {

    const divStyle = {
        backgroundImage: `url(/imgs/category-imgs/${props.category.bgImgURI})`,
    };
        
    return (
        <div className='category-order' style={divStyle}>
            <a href={'/order/products/' + props.category.id} >{props.category.name}</a>            
        </div>

    )
}

export default OrderCategory