console.log("Email Writer");

function findComposeToolbar(){
    const selectors = ['.btC', '.aDh', '[role="toolbar"]', '.gU.Up'];
    for(const selector of selectors){
        const toolbar = document.querySelector(selector);
        if(toolbar){
            return toolbar;
        }
        return null;
    }
}

function createAIButton() {

    const button = document.createElement('div');

    button.className = 'T-I J-J5-Ji aoO v7 T-I-atl L3';

    button.style.marginRight = '8px';

    button.innerHTML = 'AI Reply';

    button.setAttribute('role', 'button');

    button.setAttribute('data-tooltip', 'Generate AI Reply');

    return button;
}

function injectButton(){
    const existingButton = document.querySelector('.ai-reply-button');
    if(existingButton){
        existingButton.remove();
    }
    const toolbaar = findComposeToolbar();

    if(!toolbaar){
        console.log("Toolbar not found");
    }

    console.log("Toolbar found");
    const button = createAIButton();
    button.classList.add('.ai-reply-button');
    toolbaar.insertBefore(button, toolbar.firstChild);
}

const observer = new MutationObserver((mutations) => {

    for (const mutation of mutations) {

        const addedNodes = Array.from(mutation.addedNodes);

        const hasComposeElements = addedNodes.some(node => {

            // Ensure it's an element node
            if (node.nodeType !== Node.ELEMENT_NODE) {
                return false;
            }

            return (
                node.matches('.aDh, .btC, [role="dialog"]') ||
                node.querySelector('.aDh, .btC, [role="dialog"]')
            );
        });

        if (hasComposeElements) {
            console.log("Compose Window Detected.");
            setTimeout(injectButton,500);
        }
    }
});

observer.observe(document.body, {
    childList: true,
    subtree: true
});