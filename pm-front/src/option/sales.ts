import { get } from "svelte/store";
import { delApi, getApi, patchApi, postApi } from "./api";
import { auth } from "./auth";

function setSales () {

    const dayStatus = async (year:number, month:string) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: "/api/status/day/"+year+"/"+month, 
                accessToken : accessToken,
            }
            const getDayStatus = await getApi(options);
            return getDayStatus;

        }catch(err){
            throw err
        }
    }

    const monthStatus = async (year:number) => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: "/api/status/month/"+year, 
                accessToken : accessToken,
            }
            const getMonthStatus = await getApi(options);
            return getMonthStatus;

        }catch(err){
            throw err
        }
    }

    const yearStatus = async () => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: "/api/status/year", 
                accessToken : accessToken,
            }
            const getYearStatus = await getApi(options);
            return getYearStatus;

        }catch(err){
            throw err
        }
    }

    const todayStatus = async () => {
        const accessToken = get(auth).Authorization;
        try{
            const options = {
                path: "/api/status/today", 
                accessToken : accessToken,
            }
            const getTodayStatus = await getApi(options);
            return getTodayStatus;

        }catch(err){
            throw err
        }
    }


    return {
        dayStatus,
        monthStatus,
        yearStatus,
        todayStatus,
    }
}

export const sales = setSales();