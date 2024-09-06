import axios from "axios";

const send = async ({method='', path='', data={}, accessToken=''} = {}) => {
    const commonUrl = "http://localhost:5000";
    // const commonUrl = "http://itemrecord.com";
    const url = commonUrl + path;
    const headers = {
        "Access-Control-Allow-Origin": commonUrl,
        "Access-Control-Allow-Credetials":true,
        "content-type" : "application/json;charset=UTF-8",
        "accept" : "application/json",
        "SameSite" : "None",
        "Authorization" : accessToken,
    }

    const options = {
        method,
        url,
        headers,
        data,
        withCredentials : true,
    }

    try{
        const res = await axios(options);
        return res.data;
    }catch(err) {
        throw err
    }
}

const getApi = ({path='', accessToken=''} = {}) => {
    return send({method: 'GET', path, accessToken})
}

const postApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'POST', path, data, accessToken})
}

const putApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'PUT', path, data, accessToken})
}

const patchApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'PATCH', path, data, accessToken})
}

const delApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'DELETE', path, data, accessToken})
}

export {
    getApi,
    postApi,
    putApi,
    patchApi,
    delApi
}