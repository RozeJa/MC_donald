import './PreparationProduct.css'

const PreparationProduct = (data) => {

    const { product } = data

    return (
        <div className='preparation-product'>
            <p>{product.count}x {product.product.name} {getImprovements(product)}</p>
        </div>
    )
}

export default PreparationProduct

function getImprovements(product) {
    let improvements = []

    product.improvements.forEach(i => {
        improvements.push(i)
    });

    return improvements.length === 0 ? "" : "[" + improvements.join(", ") +  "]"
}