import { useContext, useState } from 'react'
import './ProductInOrder.css'
import OrderProvider from '../OrderProvider'

// data obsahují:   product
const ProductInOrder = (data) => {
    
    const [dialog, setDialog] = useState(<></>)

    const OrderP = useContext(OrderProvider)
    const product = data.product
    

    const handleChange = (event) => {

        const { name, value } = event.target

        let order = OrderP.order

        order.products.map(product => {
            if (product.product.id === name) {
                let newConut = Math.max(1, parseInt(value))
                newConut = Math.min(newConut, 30)
                product.count = parseInt(newConut)
            } 
            return product
        })
    }

    return (
        <div className='product-in-order'>
            {dialog}
            <p className='product-in-order-name' onClick={() => {
                if (Object.keys(product.product.availableImprovements).length > 0) {
                    // TODO: zobraz dialog pro přidání vylepšení pokud jsou dostupná
                    console.log("dialog");
                    setDialog(); 

                } 
            }}>{product.product.name} {countPrice(product)} Kč</p>
            <div className='product-in-order-controll'>
                <input type='number' min="1" max="30" name={product.product.id} defaultValue={product.count} onChange={handleChange} />
                <button onClick={() => {
                    OrderP.setOrder({ ...OrderP.order, ['products']: OrderP.order.products.filter(p => p.product.id !== product.product.id) })
                }}>X</button>                
            </div>

        </div>
    )
}

export default ProductInOrder

function countPrice(product) {
    let price = product.count * product.product.price
    
    product.improvements.forEach(improvement => {
        price += improvement.price
    });

    return price
}
