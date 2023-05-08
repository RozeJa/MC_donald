import './PreparationOrder.css'

const PreparationOrder = (data) => {
    return (
        <div className='preparation-order' onClick={() => {
            console.log("asddda");
            data.order.finished = true
            fetch('http://localhost:8080/api/orders/' + data.order.id, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data.order)
            }).then(response => {
                if (response.status === 200) {
                    console.log("odebrání se podařilo");
                }
            })
        }}>
            order.id: {data.order.id}
        </div>
    )
}

export default PreparationOrder