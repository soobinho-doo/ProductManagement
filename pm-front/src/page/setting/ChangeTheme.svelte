<script lang="ts">
    // Theme
    import { onMount } from "svelte";

    let theme = "light";

    // 테마를 토글하는 함수
    function toggleTheme() {
        theme = theme === "light" ? "dark" : "light";
        document.documentElement.setAttribute("data-theme", theme);
        localStorage.setItem("theme", theme);
    }

    // 컴포넌트가 마운트될 때 로컬스토리지에서 저장된 테마를 가져옴
    onMount(() => {
        const savedTheme = localStorage.getItem("theme");
        if (savedTheme) {
            theme = savedTheme;
            document.documentElement.setAttribute("data-theme", theme);
        }
    });
      
</script>
    


<button type="button" class="fs-1rem f-nato relative background-none border-none padding-4-8" on:click={toggleTheme}>
    {#if theme === "light"}
        <svg xmlns="http://www.w3.org/2000/svg" class="svg-size" width="24" height="24" viewBox="0 0 24 24">
            <path class="svg-fill-dark-gray" d="M12 21q-3.75 0-6.375-2.625T3 12t2.625-6.375T12 3q.35 0 .688.025t.662.075q-1.025.725-1.638 1.888T11.1 7.5q0 2.25 1.575 3.825T16.5 12.9q1.375 0 2.525-.613T20.9 10.65q.05.325.075.662T21 12q0 3.75-2.625 6.375T12 21m0-2q2.2 0 3.95-1.213t2.55-3.162q-.5.125-1 .2t-1 .075q-3.075 0-5.238-2.163T9.1 7.5q0-.5.075-1t.2-1q-1.95.8-3.163 2.55T5 12q0 2.9 2.05 4.95T12 19m-.25-6.75"/>
        </svg>
    {:else}
        <svg xmlns="http://www.w3.org/2000/svg" class="svg-size" width="24" height="24" viewBox="0 0 24 24">
            <g fill="none" stroke="#e2ca32" stroke-width="2"><circle cx="12" cy="12" r="4" stroke-linejoin="round"/>
                <path stroke-linecap="round" d="M20 12h1M3 12h1m8 8v1m0-18v1m5.657 13.657l.707.707M5.636 5.636l.707.707m0 11.314l-.707.707M18.364 5.636l-.707.707"/>
            </g>
        </svg>
    {/if}
</button>

<!-- <label class="display-flex align-items">
    <input role="switch" type="checkbox" class="cursor-pointer" bind:checked={toggleSwitch} on:change={toggleTheme}/>
</label> -->
<!-- <style>
    [type="checkbox"] {
        appearance: none;
        position: relative;
        border: max(2px, 0.1em) solid gray;
        border-radius: 1.25em;
        width: 2.75em;
        height: 1.5em;
    }

    [type="checkbox"]::before {
        content: "";
        position: absolute;
        left: 0;
        width: 1.25em;
        height: 1.25em;
        border-radius: 50%;
        transform: scale(0.8);
        background-color: gray;
        transition: left 250ms linear;
    }

    [type="checkbox"]:checked {
        background-color: tomato;
        border-color: tomato;
    }

    [type="checkbox"]:checked::before {
        background-color: white;
        left: 1.25em;
    }

    [type="checkbox"]:disabled {
        border-color: lightgray;
        opacity: 0.7;
    }

    [type="checkbox"]:disabled:before {
        background-color: lightgray;
    }

    [type="checkbox"]:enabled:hover {
        box-shadow: 0 0 0 max(4px, 0.2em) lightgray;
    }
</style> -->