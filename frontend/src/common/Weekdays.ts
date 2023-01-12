class Weekdays {
    private static weekdays: string[] = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    ]

    static fromNumber(dayOfWeek: number): string {
        if (dayOfWeek >= 0 && dayOfWeek <= 6) {
            return this.weekdays[dayOfWeek];
        }

        return "unknown";
    }
}

export default Weekdays;