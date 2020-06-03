export default function() {
    return {
        user: JSON.parse(localStorage.getItem("user"))
    }
}