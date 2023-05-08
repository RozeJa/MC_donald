import './Error.css'

// data obsahují: code
//                call
const Error = (data) => {
    return (
        <>
            <h1>Error: {data.code} {data.call}</h1>
        </>
    )
}

export default Error