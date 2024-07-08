import { get } from "svelte/store";
import { delApi, getApi, patchApi, postApi, putApi } from "./api";
import { auth } from "./auth";

function setDashBoard () {

    const stockList = async () => {
        const accessToken = get(auth).Authorization;

        try{
            const options = {
                path: '/api/product/dashboard', 
                accessToken:accessToken
            }
            const getStockList:any = await getApi(options);
            return getStockList;

        }catch(err){
            throw err
        }
    }

    return {    
        stockList,
    }
}

export const dashBaord = setDashBoard();