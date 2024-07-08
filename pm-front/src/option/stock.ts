import { get } from "svelte/store";
import { delApi, getApi, patchApi, postApi } from "./api";
import { auth } from "./auth";
import { noti } from "./store";
import { push } from "svelte-spa-router";

function setStock () {

    const list = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: '/api/stock/paging', 
                data: data,
                accessToken : accessToken,
            }
            const stockList = await postApi(options);
            return stockList;

        }catch(err){
            throw err
        }
    }
    const info = async (sq:number) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: "/api/stock/"+sq,
                accessToken : accessToken,
            }
            const stockInfo = await getApi(options);
            return stockInfo;

        }catch(err){
            throw err
        }
    }
    const input = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: '/api/stock', 
                data: data,
                accessToken : accessToken,
            }
            const stockInput = await postApi(options);
            if(stockInput === 1){
                push("/stock/list")
                noti.success("재고 등록 완료",2000);
            }
        }catch(err){
            throw err
        }
    }
    const modify = async (sq:number, data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: '/api/stock', 
                data : data, 
                accessToken : accessToken,
            }
            const stockModify = await patchApi(options);
            return stockModify;
            
        }catch(err){
            throw err
        }
    }
    const del = async (data:any) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: '/api/stock/delete', 
                data: data,
                accessToken : accessToken,
            }
            const del = await postApi(options);
            return del;

        }catch(err){
            throw err
        }
    }

    return {
        list,
        info, 
        input,
        modify,
        del,
    }
}

export const stock = setStock();