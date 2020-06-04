const titleText = (state, value) => {
    state.titleText = value;
};
const appendCart = (state, value) => {
    /**
     * @param product value */
    console.log(value);
    if (state.cart.length) {
        let flag = true;
        for (let i = 0; i < state.cart.length; i++) {
            if (state.cart[i].id == value.id) {
                state.cart[i].count += value.count;
                flag = false;
                break;
            }
        }
        if (flag) {
            state.cart.push(Object.assign({}, value));
        }
    } else {
        state.cart.push(Object.assign({}, value));
    }
    console.log(state.cart);
};
export { titleText, appendCart };