import { Input } from 'reactstrap'
import './ImprovementInProduct.css'

const ImprovementInProduct = (props) => {
    return (
        <div className='improvement-in-product'>
            <p>{props.improvement.name} {props.improvement.price} Kč</p>
            <Input type='checkbox' name={props.improvement.id} onChange={props.handleChange} checked={props.improvements.find(i => i.id === props.improvement.id)} />
        </div>
    )
}

export default ImprovementInProduct