<script lang="ts">
    
    let toggleSwitch = false;

    const switchTheme = () => {        
        if (toggleSwitch) {
            document.documentElement.setAttribute("data-theme", "dark");
        } else {
            document.documentElement.setAttribute("data-theme", "light");
        }        
    }
      
</script>
    
<label class="display-flex align-items">
    <input role="switch" type="checkbox" class="cursor-pointer" bind:checked={toggleSwitch} on:change={switchTheme}/>
</label>
    
<style>
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
</style>