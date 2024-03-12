// 가격 3자리마다 , 찍기
export const priceReplace = (price:any) => { 
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 소수점 반올림, 반올림
export const mathRound = (price:number) => {
    return Math.round(price)
}

// 소수점 무조건 반올림
export const mathCeil = (price:number) => {
    return Math.ceil(price);
}