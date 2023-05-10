const compare = (p, product, improvements) => {
    let sameProduct = p.product.id === product.id 
    let sameImprovements = p.improvements.filter(improvement => {
        
        return improvements.find(i => i.id === improvement.id) === undefined  
    }).length === 0 && p.improvements.length !== 0

    return (sameProduct && sameImprovements) || (p.improvements.length === 0 && sameProduct && improvements.length === 0)
}

export default compare