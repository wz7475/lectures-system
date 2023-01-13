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

    private static shortWeekdays: string[] = [
        "Mon.",
        "Tues.",
        "Wed.",
        "Thurs.",
        "Fri.",
        "Sat.",
        "Sun."
    ]

    static fromNumber(dayOfWeek: number): string {
        if (dayOfWeek >= 0 && dayOfWeek <= 6) {
            return this.weekdays[dayOfWeek];
        }

        return "unknown";
    }

    static shortNameFromNumber(dayOfWeek: number): string {
        if (dayOfWeek >= 0 && dayOfWeek <= 6) {
            return this.shortWeekdays[dayOfWeek];
        }

        return "unknown";
    }
}

export default Weekdays;