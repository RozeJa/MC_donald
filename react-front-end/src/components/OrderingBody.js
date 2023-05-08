import { useContext, useEffect, useState } from 'react'
import './OrderingBody.css'
import OrderProvider from '../OrderProvider'

// data obsahují:   products
const OrderingBody = (data) => {

    const OrderP = useContext(OrderProvider)
    const [produstsShow, setProductsShow] = useState([])
    const [dialog, setDialog] = useState(<></>)

    const addProduct = (product) => {

        if (Object.keys(product.availableImprovements).length > 0) {
            // TODO: pokud extují vylepšení zobraz dialog
            console.log("dialog");
            setDialog(); 
        } else {
    
            let products = OrderP.order.products

            let selectedProduct = products.find(p => p.product.id === product.id);

            if (selectedProduct === undefined) {
                OrderP.order.products.push({
                    count: 1,
                    product: product,
                    improvements: []
                })                
            } else {
                products.map(p => {
                    if (p.product.id === selectedProduct.product.id) {
                        selectedProduct.count++
                        return selectedProduct
                    } else {
                        return p
                    }
                })
            }
    
            OrderP.setOrder({ ...OrderP.order, ['products']: products })
        }
    }

    useEffect(() => {
        setProductsShow(data.products.map(product => {
            return  <div className='product-order-body' key={product.id} onClick={() => addProduct(product)}>
                        <h2>{product.name}</h2>
                        <h3>{product.price} Kč</h3>
                    </div>
        }))
    }, [data.products, OrderP.order])

    return (
        <div className='order-body'>
            {dialog}
            {produstsShow}
        </div>
    )
}

export default OrderingBody
