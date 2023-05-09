import { Input } from 'reactstrap'
import './ImprovementInProduct.css'

const ImprovementInProduct = (props) => {
    return (
        <div className='improvement-in-product'>
            <p>{props.improvement.name}</p>
            <Input type='checkbox' name={props.improvement.id} onChange={props.handleChange} />
        </div>
    )
}

export default ImprovementInProduct