import './Home.css'

const Home = () => {
    return (
        <div className='home'>
            <a href="/order">Obědnávací panel</a>
            <a href="/preparation">Panel v přípravě</a>
            <a href="/delivery">Panel výdeje</a>
        </div>
    )
}

export default Home