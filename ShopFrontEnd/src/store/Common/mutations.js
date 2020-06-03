const user = (state, value) => {
    localStorage.setItem("user", JSON.stringify(value));
    state.user = value;
};

export { user };