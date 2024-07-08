import { writable, readable, derived } from "svelte/store";

// Toast 
const createNotificationStore = () => {
    const _notifications = writable([]);

    const send = (message:any, type = "default", timeout:any) => {
        _notifications.update( (state):any => {
            return [...state, { id: id(), type, message, timeout }]
        })
    }

    const notifications:any = derived(_notifications, ($_notifications:any, set) => {
        set($_notifications)
        if ($_notifications.length > 0) {
            const timer = setTimeout(() => {
                _notifications.update( (state) => {
                    state.shift()
                    return state
                })
            }, $_notifications[0].timeout)
            return () => {
                clearTimeout(timer)
            }
        }
    })
    const { subscribe } = notifications

    return {
        subscribe,
        send,
        default: (msg:string, timeout:number) => send(msg, "default", timeout),
        danger: (msg:string, timeout:number) => send(msg, "danger", timeout),
        warning: (msg:string, timeout:number) => send(msg, "warning", timeout),
        info: (msg:string, timeout:number) => send(msg, "info", timeout),
        success: (msg:string, timeout:number) => send(msg, "success", timeout),
    }
}
const id =() => {
    return '_' + Math.random().toString(36).substr(2, 9);
};


// Export 
export const noti:any = createNotificationStore(); // 토스트