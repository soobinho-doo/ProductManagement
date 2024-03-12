import { writable, readable } from "svelte/store";

// 로그인 판별
export const isConnection = writable(false);

// 로그인 시간 정보
export const connectionTime = writable(1000 * 60 * 1);

// 밝은, 다크 모드 
export const isTheme = writable(true);
