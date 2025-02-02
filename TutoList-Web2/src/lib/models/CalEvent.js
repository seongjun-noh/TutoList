export class CalendarEvent {
  constructor({ id, title, start, end, description, allDay = false, rrule }) {
      this.id = id;
      this.title = title;
      this.start = new Date(start);
      this.end = end ? new Date(end) : null;
      this.description = description;
      this.allDay = allDay;
      this.rrule = rrule;
  }
}
