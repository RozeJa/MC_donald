import './DistributionOrder.css'

const DistributionOrder = (data) => {
    return (
        <div className='distribution-order' onClick={() => data.collback(data.order)}>
            <h2>{data.order.number}</h2>
        </div>
    )
}

export default DistributionOrder