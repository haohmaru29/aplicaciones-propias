/*!
 * Extensible 1.5.0
 * Copyright(c) 2010-2011 Extensible, LLC
 * licensing@ext.ensible.com
 * http://ext.ensible.com
 */
/*
 * Spanish/Spain locale
 * By Alberto López Doñaque, <lopezdonaque@gmail.com>
 */

Extensible.Date.use24HourTime = true;

if(Extensible.calendar.view.AbstractCalendar) {
    Ext.apply(Extensible.calendar.view.AbstractCalendar.prototype, {
        startDay: 1,
        todayText: 'Hoy',
        defaultEventTitleText: '(Sin título)',
        ddCreateEventText: 'Crear evento desde {0}',
        ddMoveEventText: 'Mover evento a {0}',
        ddResizeEventText: 'Actualizar evento a {0}'
    });
}

if(Extensible.calendar.view.Month) {
    Ext.apply(Extensible.calendar.view.Month.prototype, {
        moreText: '+{0} m&aacute;s...',
        getMoreText: function(numEvents){
            return '+{0} m&aacute;s...';
        },
        detailsTitleDateFormat: 'j \\de F'
    });
}

if(Extensible.calendar.CalendarPanel) {
    Ext.apply(Extensible.calendar.CalendarPanel.prototype, {
        todayText: 'Hoy',
        dayText: 'Día',
        weekText: 'Semana',
        monthText: 'Mes',
        jumpToText: 'Ir a:',
        goText: 'Ir',
        multiDayText: '{0} días',
        multiWeekText: '{0} semanas',
        getMultiDayText: function(numDays){
            return '{0} días';
        },
        getMultiWeekText: function(numWeeks){
            return '{0} semanas';
        }
    });
}

if(Extensible.calendar.form.EventWindow) {
    Ext.apply(Extensible.calendar.form.EventWindow.prototype, {
        width: 600,
        labelWidth: 65,
        titleTextAdd: 'A&ntilde;adir evento',
        titleTextEdit: 'Editar evento',
        savingMessage: 'Guardando cambios...',
        deletingMessage: 'Borrando evento...',
        detailsLinkText: 'Editar detalles...',
        saveButtonText: 'Guardar',
        deleteButtonText: 'Borrar',
        cancelButtonText: 'Cancelar',
        titleLabelText: 'Título',
        datesLabelText: 'Cuando',
        calendarLabelText: 'Calendario'
    });
}

if(Extensible.calendar.form.EventDetails) {
    Ext.apply(Extensible.calendar.form.EventDetails.prototype, {
        labelWidth: 75,
        labelWidthRightCol: 75,
        title: 'Formulario de evento',
        titleTextAdd: 'A&ntilde;adir evento',
        titleTextEdit: 'Editar evento',
        saveButtonText: 'Guardar',
        deleteButtonText: 'Borrar',
        cancelButtonText: 'Cancelar',
        titleLabelText: 'Título',
        datesLabelText: 'Cuando',
        reminderLabelText: 'Recordatorio',
        notesLabelText: 'Notas',
        locationLabelText: 'Localizaci&oacute;n',
        webLinkLabelText: 'Enlace Web',
        calendarLabelText: 'Calendario',
        repeatsLabelText: 'Repetir'
    });
}

if(Extensible.form.field.DateRange) {
    Ext.apply(Extensible.form.field.DateRange.prototype, {
        toText: 'a',
        allDayText: 'Todo el día'
    });
}

if(Extensible.calendar.form.field.CalendarCombo) {
    Ext.apply(Extensible.calendar.form.field.CalendarCombo.prototype, {
        fieldLabel: 'Calendario'
    });
}

if(Extensible.calendar.gadget.CalendarListPanel) {
    Ext.apply(Extensible.calendar.gadget.CalendarListPanel.prototype, {
        title: 'Calendarios'
    });
}

if(Extensible.calendar.gadget.CalendarListMenu) {
    Ext.apply(Extensible.calendar.gadget.CalendarListMenu.prototype, {
        displayOnlyThisCalendarText: 'Mostrar solo este calendario'
    });
}

if(Extensible.form.recurrence.Combo) {
    Ext.apply(Extensible.form.recurrence.Combo.prototype, {
        fieldLabel: 'Repeats',
        recurrenceText: {
            none: 'No repetir',
            daily: 'Diario',
            weekly: 'Semanal',
            monthly: 'Mensual',
            yearly: 'Anual'
        }
    });
}

if(Extensible.calendar.form.field.ReminderCombo) {
    Ext.apply(Extensible.calendar.form.field.ReminderCombo.prototype, {
        fieldLabel: 'Recordatorio',
        noneText: 'Ninguno',
        atStartTimeText: 'Al inicio',
        getMinutesText: function(numMinutes){
            return numMinutes === 1 ? 'minuto' : 'minutos';
        },
        getHoursText: function(numHours){
            return numHours === 1 ? 'hora' : 'horas';
        },
        getDaysText: function(numDays){
            return numDays === 1 ? 'día' : 'días';
        },
        getWeeksText: function(numWeeks){
            return numWeeks === 1 ? 'semana' : 'semanas';
        },
        reminderValueFormat: '{0} {1} antes de empezar' // e.g. "2 hours before start"
    });
}

if(Extensible.form.field.DateRange) {
    Ext.apply(Extensible.form.field.DateRange.prototype, {
        dateFormat: 'd/m/Y'
    });
}

if(Extensible.calendar.menu.Event) {
    Ext.apply(Extensible.calendar.menu.Event.prototype, {
        editDetailsText: 'Editar detalles',
        deleteText: 'Borrar',
        moveToText: 'Mover a...'
    });
}

if(Extensible.calendar.dd.DropZone) {
    Ext.apply(Extensible.calendar.dd.DropZone.prototype, {
        dateRangeFormat: '{0}-{1}',
        dateFormat: 'd/m' // e.g. "25/12"
    });
}

if(Extensible.calendar.dd.DayDropZone) {
    Ext.apply(Extensible.calendar.dd.DayDropZone.prototype, {
        dateRangeFormat: '{0}-{1}',
        dateFormat : 'd/m' // e.g. "25/12"
    });
}

if(Extensible.calendar.template.BoxLayout) {
    Ext.apply(Extensible.calendar.template.BoxLayout.prototype, {
        firstWeekDateFormat: 'D d', // e.g. "Lun 01"
        otherWeeksDateFormat: 'd',
        singleDayDateFormat: 'l, d \\de F \\de Y', // e.g. "Lunes, 12 de Enero de 2011"
        multiDayFirstDayFormat: 'd M, Y', // e.g. "09 Ene, 2011"
        multiDayMonthStartFormat: 'd M' // e.g. "01 Ene"
    });
}

if(Extensible.calendar.template.Month) {
    Ext.apply(Extensible.calendar.template.Month.prototype, {
        dayHeaderFormat: 'D',
        dayHeaderTitleFormat: 'l, d \\de F \\de Y' // e.g. "Lunes, 12 de Enero de 2011"
    });
}
