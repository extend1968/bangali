/*****************************************************************************************************************
*
*  A SmartThings child smartapp which creates the "room" device using the rooms occupancy DTH.
*  Copyright (C) 2017 bangali
*
*  Contributors:
*   https://github.com/Johnwillliam
*
*  License:
*  This program is free software: you can redistribute it and/or modify it under the terms of the GNU
*  General Public License as published by the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
*
*  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
*  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
*  for more details.
*
*  You should have received a copy of the GNU General Public License along with this program.
*  If not, see <http://www.gnu.org/licenses/>.
*
*  Name: Room Child App
*  Source: https://github.com/adey/bangali/blob/master/smartapps/bangali/rooms-child-app.src/rooms-child-app.groovy
*
*  Version: 0.08.6
*
*   DONE:   12/17/2017
*   1) added support for variable text for arrival and departure announcements.
*   2) added support for power level to set room to engaged.
*
*  Version: 0.08.5
*
*   DONE:   12/16/2017
*   1) added support for arrival and departure announcement.
*   2) added support for speaker control through rules and use of speaker to set a room to engaged.
*   3) bug fix to stop truncating temperature to integer.
*
*  Version: 0.08.3
*
*   DONE:   12/12/2017
*   1) added support for wake and sleep times to calculate level and color temperature.
*   2) added support to process rules every 15 minutes so switches state/level/color temperature is updated even
*       when there is no motion in room but there are switches on.
*   3) fix for continuous motion with motion sensor.
*
*  Version: 0.08.1
*
*   DONE:   12/10/2017
*   1) added support for auto level which automatically calculates light level and optionally color temperature to
*       to be set based on local sunrise and sunset times. this does not yet use circadian rhytym based calculation.
*
*  Version: 0.08.0
*
*   DONE:   12/8/2017
*   1) added support to reset room state from ENAGED or ASLEEP when another room changes to ENGAGED or ASLEEP
*   2) added support to reset room state when another room changes to ENGAGED or ASLEEP.
*   3) removed lux threshold support from main settings since this is now available under rules.
*   4) fixed presence indicator for device display.
*   5) added support for multiple engaged switches.
*   6) added undimming for lights.
*	7) added support for centigrade display.
*   8) added support for multiple presence sensors.
*   9) couple of bug fixes.
*
*  Version: 0.07.5
*
*   DONE:   12/5/2017
*   1) added support to reset room state from ENAGED or ASLEEP when another room changes to ENGAGED or ASLEEP
*   2) added right temperature scale support
*   3) fixed couple of bugs
*   4) added support for date filtering in rules
*
*  Version: 0.07.3
*
*   DONE:   12/2/2017
*   1) added support for executing piston instead of just turning on a light
*   2) added view all settings
*   3) added room device indicators to the room device so they can be seen in one place
*   4) added timer to room which counts down in increments of 5
*   5) some bug fixes.
*
*  Version: 0.07.1
*
*   DONE:   11/28/2017
*   1) Fixed removed code
*   2) Added ability to choose action for night button
*
*  Version: 0.07.0
*
*   DONE:   11/27/2017
*   1) instead of adding swtiches to individual settings created rules to allow switches to be turned on and off
*       and routines to be executed via this rule. VACANT state automatically turns of the switches the last rule
*       turned on unless user creates a rule for VACANT state in which case the automatic turning off of switches
*       on VACANT state is skipped instead the rules are checked and executed for the VACANT state.
*   2) some bug fixes.
*
*  Version: 0.05.9
*
*   DONE:   11/21/2017
*   1) changed name of 'occupancyStatus' to just 'occupancy' to be consistent with ST.
*   2) added switches to turn on and off when room chnages to asleep. switches set to turn on are also turned off
*           when room changes away from asleep.
*   2) some bug fixes.
*
*  Version: 0.05.8
*
*   DONE:   11/20/2017
*   1) Changed configuration pages
*
*  Version: 0.05.7
*
*   DONE:   11/20/2017
*   1) added support for room busy check and setting ENGAGED state based on how busy room is.
*   2) added support for arrival and/or departure action when using presence sensor.
*   3) some bug fixes.
*
*  Version: 0.05.5
*
*   DONE:   11/19/2017
*   1) added sleepSensor feature and corresponding settings by https://github.com/Johnwillliam.
*   2) some bug fixes.
*
*  Version: 0.05.2
*
*   DONE:   11/16/2017
*   1) changed from 10 to 12 device settings and added adjacent rooms to devices display.
*   2) some bug fixes.
*
*  Version: 0.05.1
*
*   DONE:   11/15/2017
*   1) added setting to select which days of week this rooms automation should run.
*
*  Version: 0.05.0
*
*   DONE:   11/13/2017
*   1) expanded the adjacent room settings. if you specify adjacent rooms you can choose 2 options:
*       i) if there is motion in an adjacent room you can force the current room to check for motion and on no
*           motion change room state to vacant.
*      ii) if there is motion in an adjacent room you can turn on lights in this room if it is currently vacant.
*           this allows for the adjacent rooms feature to be used as a light your pathway can kind of setup.
*   2) some bug fixes.
*
*  Version: 0.04.6
*
*   DONE:   11/12/2017
*   1) bug fixes around contact sensors.
*
*  Version: 0.04.5
*
*   DONE:   11/10/2017
*   1) revamped device details screen. if users dont like it will revert back.
*   2) when swiches are turned off because lux rose or is outside of time window added settings to turn off both
*           group of switches instead of just switches off.
*   3) added option to change state directly from engaged to vacant without moving to checking state.
*	4) removed last event from status message.
*
*  Version: 0.04.3
*
*   DONE:   11/8/2017
*   1) added last event to status message.
*   2) added concept of adjacent rooms that you can select in room settings. setting does not do anything yet :-)
*
*  Version: 0.04.2
*
*   DONE:   11/6/2017
*   1) added setting option to allow timeout from last motion active or on motion inactive. if motion has a long timeout
*           this will allow the lights to turn off quicker. but be aware motion sensor may show motion due to long
*           timeout while room indicates its vacant.
*
*  Version: 0.04.1
*
*   DONE:   11/5/2017
*   1) added support for time window to turn on/off switches when between those times. this works with other settings
*           as well. like if lux is specified both the lux setting and the time setting have to be true for switches
*           to be turned on or off.
*
*  Version: 0.04
*
*   DONE:   11/3/2017
*   1) added support for presence sensor to change room state to engaged when present. when presence sensor is not
*           present the room automation should work normally.
*   2) added support for modes which when set cause all automation to be bypassed if location is any of those modes.
*
*  Version: 0.03.7
*
*   DONE:   11/1/2017
*   1) added support for contact sensor. when contact sensor changes to closed room will be set to checking state.
*           if there is no motion afterwards room will be set to vacant. if there is motion, room will be set to
*           engaged which stops room automation from kicking in till the contact is opened again.
*           when contact sensor changes to open room will be set to checking state so automation can resume again.
*           the only exception to this is home changing to away in which case room will be set to vacant.
*   2) when contact sensor is specified but no motion sensor is specified room will be changed to engaged when
*           contact sensor closes.
*   3) if there is a motion sensor specified but no motion timeout value then room will be changed to vacant when
*           motion sensor becomes inactive and room is in occupied or checking state.
*   4) added engaged switch which when turned on will mark the room as engaged to stop automation. this gets a
*           little tricky when both engaged switch and contact sensor is defined. the contact sensor changing to
*           open will reset the state back to checking. but if there is subsequent motion in the room within the
*           timeout period the room will be set to occupied. or if the door is closed again and there is subsequent
*           motion in the room within the timeout period the room will be set to engaged stopping automation.
*   5) added lights control with lux for engaged state.
*   6) added button push to toogle room state between engaged and checking when room state is already engaged.
*
*  Version: 0.03.5
*
*   DONE:   10/29/2017
*   1) added support for setting level and/or color temperature for turning on switches. these will be set for
*           those devices in the turn on switchs list that support it.
*   2) since motion inactive timeout can vary so widely amongst different brands of motion sensors chose not to
*           use motion inactive event and instead timeout on motion active event for predictable user experience.
*   3) added support for dimming before turning off light.
*   4) added support for color setting which takes preference over color temperature if the switch supports it.
*   5) fixed small bugs.
*
*  Version: 0.03.1
*
*   DONE:   10/27/2017
*   1) added support for lux sensor and lux value. if these values are specified:
*       a) if lux value falls <= that value and switches on are selected those switches will be turned on.
*       b) if lux value rises > that value and switches off are selected those switches will be turned off.
*       c) switches on with motion will be turned on only when lux value is <= that value.
*   2) fixed small bugs.
*
*  Version: 0.03
*
*   DONE:
*   1) added new states do not disturb and asleep, on user demand. these have button value of 7 and 8 respectively.
*	2) locked and kaput moved below the fold and replaced on-screen with do not disturb and asleep respectively.
*   3) cleaned up settings display.
*   4) changed roomOccupancy to occupancyStatus. sorry for the compatibility breaking change. by user demand.
*   5) updated some interstitial text.
*   6) if no motion sensor specified but there is a timeout value >= 5 and turn off switches specified, those
*            switches will be switched off after timeout seconds if room is vacant.
*	7) added new engaged state, on user demand. this button has a button value of 9 respectively.
*   8) if room state changes any pending actions are cancelled.
*
*  Version: 0.02
*
*   DONE:
*	0) Initial commit.
*   1) added support for multiple away modes. when home changes to any these modes room is set to vacant but
*            only if room is in occupied or checking state.
*   2) added subscription for motion devices so if room is vacant or checking move room state to occupied.
*   3) added support for switches to be turned on when room is changed to occupied.
*   4) added support for switches to be turned off when room is changed to vacant, different switches from #3.
*   5) added button push events to tile commands, where occupied = button 1, ..., kaput = button 6 so it is
*           supported by ST Smart Lighting smartapp.
*
*****************************************************************************************************************/

private isDebug()   {  return true  }

definition	(
    name: "rooms child app",
    namespace: "bangali",
    parent: "bangali:rooms manager",
    author: "bangali",
    description: "DO NOT INSTALL DIRECTLY. Rooms child smartapp to create new rooms using 'rooms occupancy' DTH from Rooms Manager smartapp.",
    category: "My Apps",
    iconUrl: "https://cdn.rawgit.com/adey/bangali/master/resources/icons/roomOccupancy.png",
    iconX2Url: "https://cdn.rawgit.com/adey/bangali/master/resources/icons/roomOccupancy@2x.png",
    iconX3Url: "https://cdn.rawgit.com/adey/bangali/master/resources/icons/roomOccupancy@3x.png"
)

preferences {
	page(name: "roomName", title: "Room Name and Settings")
    page(name: "pageOccupiedSettings", title: "Occupied State Settings")
    page(name: "pageEngagedSettings", title: "Engaged State Settings")
    page(name: "pageCheckingSettings", title: "Checking State Settings")
    page(name: "pageVacantSettings", title: "Vacant State Settings")
    page(name: "pageLuxTimeSettings", title: "Lux & Time Settings")
    page(name: "pageSpeakerSettings", title: "Speaker Settings")
    page(name: "pagePowerSettings", title: "Power Settings")
    page(name: "pageAutoLevelSettings", title: "Light Auto Level Settings")
    page(name: "pageRules", title: "Maintain Lighting Rules")
    page(name: "pageRule", title: "Edit Lighting Rule")
    page(name: "pageRuleDate", title: "Edit Lighting Rule Date")
    page(name: "pageRuleTime", title: "Edit Lighting Rule Time")
    page(name: "pageNightMode", title: "Night Mode Settings")
    page(name: "pageAdjacentRooms", title: "Adjacent Rooms Settings")
    page(name: "pageGeneralSettings", title: "General Settings")
    page(name: "pageAllSettings", title: "All Settings")
}

def roomName()	{
    def roomNames = parent.getRoomNames(app.id)
    def luxAndTimeSettings = (luxSensor || timeSettings)
    def autoLevelSettings = (minLevel || maxLevel || state.ruleHasAL || autoColorTemperature)
    def timeSettings = (fromTimeType || toTimeType)
    def adjRoomSettings = (adjRooms ? true : false)
    def miscSettings = (awayModes || pauseModes || dayOfWeek)
    def engagedSettings = (busyCheck || engagedButton || buttonIs || personsPresence || engagedSwitch || contactSensor || noMotionEngaged)
//    def luxSettings = (luxSensor || luxThreshold)
//    def luxAndTimeSettings = (luxSettings || timeSettings)
    def asleepSettings = (asleepSensor || nightSwitches)
    state.passedOn = false
	dynamicPage(name: "roomName", title: "Room Name", install: true, uninstall: childCreated())		{
        section		{
            if (!childCreated())
				label title: "Room Name:", required: true
            else
                paragraph "Room Name:\n${app.label}"
		}
        section		{
            paragraph "FOLLOWING SETTINGS ARE ALL OPTIONAL. CORRESPONDING ACTIONS WILL BE SKIPPED WHEN SETTING IS BLANK. WHEN SPECIFIED SETTINGS WORK IN COMBINATION WHEN THAT MAKES SENSE."
        }
        section("") {
				href "pageOccupiedSettings", title: "OCCUPIED SETTINGS", description: (motionSensors ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageEngagedSettings", title: "ENGAGED SETTINGS", description: (engagedSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageCheckingSettings", title: "CHECKING SETTINGS", description: ((dimTimer || dimByLevel) ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageVacantSettings", title: "VACANT SETTINGS", description: (turnOffMusic ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageLuxTimeSettings", title: "LUX SETTING", description: (luxAndTimeSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageSpeakerSettings", title: "SPEAKER SETTING", description: (musicDevice ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pagePowerSettings", title: "POWER SETTING", description: (powerMeter ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageAutoLevelSettings", title: "AUTO LEVEL 'AL' SETTINGS", description: (autoLevelSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageRules", title: "LIGHTING RULES", description: "Maintain rules"
		}
        section("") {
				href "pageNightMode", title: "ASLEEP SETTINGS", description: (asleepSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageAdjacentRooms", title: "ADJACENT ROOMS SETTINGS", description: (adjRoomSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageGeneralSettings", title: "MODE AND OTHER SETTINGS", description: (miscSettings ? "Tap to change existing settings" : "Tap to configure")
		}
        section("") {
				href "pageAllSettings", title: "VIEW ALL SETTINGS", description: "Tap to view all settings"
		}
        remove("Remove Room", "Remove Room ${app.label}")
	}
}

private pageOccupiedSettings()      {
	dynamicPage(name: "pageOccupiedSettings", title: "", install: false, uninstall: false)     {
        section("MOTION SENSOR CONFIGURATION FOR OCCUPIED STATE:", hideable: false)        {
            input "motionSensors", "capability.motionSensor", title: "Which motion sensor?", required: false, multiple: true, submitOnChange: true
            if (motionSensors)
                input "noMotion", "number", title: "Motion timeout after how many seconds?", required: false, multiple: false, defaultValue: null, range: "5..99999", submitOnChange: true
            else
                paragraph "Motion timeout after how many seconds?\nselect motion sensor above to set"
            if (noMotion)
                input "whichNoMotion", "enum", title: "Use which motion event?", required: true, multiple: false, defaultValue: 2, submitOnChange: true,
                                                                                        options: [[1:"Last Motion Active"],[2:"Last Motion Inactive"]]
            else
                paragraph "Use which motion event?\nselect number of seconds above to set"
        }
	}
}

private pageEngagedSettings() {
    def buttonNames = [[1:"One"],[2:"Two"],[3:"Three"],[4:"Four"],[5:"Five"],[6:"Six"],[7:"Seven"],[8:"Eight"],[9:"Nine"],[10:"Ten"],[11:"Eleven"],[12:"Twelve"]]
    def engagedButtonOptions = [:]
    if (engagedButton)      {
        def engagedButtonAttributes = engagedButton.supportedAttributes
        def attributeNameFound = false
        engagedButtonAttributes.each  { att ->
            if (att.name == 'occupancy')
                buttonNames = [[1:'occupied'], [2:'checking'], [3:'vacant'], [4:'locked'], [5:'reserved'], [6:'kaput'], [7:'donotdisturb'], [8:'asleep'], [9:'engaged']]
            if (att.name == 'numberOfButtons')
                attributeNameFound = true
        }
        def numberOfButtons = engagedButton.currentValue("numberOfButtons")
        if (attributeNameFound && numberOfButtons)      {
            def i = 0
            for (; i < numberOfButtons; i++)
                engagedButtonOptions << buttonNames[i]
        }
        else
            engagedButtonOptions << [null:"No buttons"]
    }
    def roomDevices = parent.getRoomNames(app.id)
	dynamicPage(name: "pageEngagedSettings", title: "", install: false, uninstall: false) {
		section("CHANGE ROOM TO 'ENGAGED' WHEN?\n(if specified this will also reset room state to 'vacant' when the button is pushed again or presence sensor changes to not present etc.)", hideable: false)		{
            paragraph "SETTINGS ARE IN ORDER OF PRIORITY IN WHICH THEY ARE CHECKED. FOR EXAMPLE, IF THERE IS BOTH AN ENGAGED SWITCH AND CONTACT SENSOR THE ENGAGED SWITCH WHEN ON WILL TAKE PRIORITY OVER THE CONTACT SENSOR BEING OPEN."
            if (motionSensors)
                input "busyCheck", "enum", title: "When room is busy?", required: false, multiple: false, defaultValue: null,
                                                                options: [[null:"No auto engaged"],[3:"Light traffic"],[5:"Medium Traffic"],[7:"Heavy Traffic"]]
            else
                paragraph "When room is busy?\nselect motion sensor(s) above to set."
            input "engagedButton", "capability.button", title: "Button is pushed?", required: false, multiple: false, submitOnChange: true
            if (engagedButton)
                input "buttonIs", "enum", title: "Button Number?", required: true, multiple: false, defaultValue: null, options: engagedButtonOptions
            else
                paragraph "Button Number?\nselect button to set"
            input "personsPresence", "capability.presenceSensor", title: "Presence sensor?", required: false, multiple: true, submitOnChange: true
            if (personsPresence)
                input "presenceAction", "enum", title: "Arrival or Departure or Both?", required: true, multiple: false, defaultValue: 3,
                                                    options: [[1:"Set state to ENGAGED on Arrival"],[2:"Set state to VACANT on Departure"],[3:"Both actions"],[4:"Neither action"]]
            else
                paragraph "Arrival or Departure or Both?\nselect presence sensor above to set"
            input "engagedSwitch", "capability.switch", title: "Switch turns ON?", required: false, multiple: true
            if (powerDevice)
                input "powerValue", "number", title: "Power value to set room to engaged?", required: false, multiple: false, defaultValue: null
            else
                paragraph "Power value to set room to engaged?\nselect power device in power settings to set."
            input "contactSensor", "capability.contactSensor", title: "Contact sensor closes?", required: false, multiple: false
            if (musicDevice)
                input "musicEngaged", "bool", title: "Set room to engaged when music starts playing?", required: false, multiple: false, defaultValue: false
            else
                paragraph "Set room to engaged when music is playing?\nselect music device in speaker settings to set."
            input "noMotionEngaged", "number", title: "Require motion within how many seconds when room is ENGAGED?", required: false, multiple: false, defaultValue: null, range: "5..99999"
            input "anotherRoomEngaged", "enum", title: "Reset ENGAGED state when another room changes to ENGAGED? If yes, which room?", required: false, multiple: false, defaultValue: null, options: roomDevices, submitOnChange: true
            input "resetEngagedDirectly", "bool", title: "When resetting room from 'ENGAGED' directly move to 'VACANT' state?", required: false, multiple: false, defaultValue: false
        }
	}
}

private pageCheckingSettings()      {
	dynamicPage(name: "pageCheckingSettings", title: "", install: false, uninstall: false)     {
        section("CHECKING STATE TIMER BEFORE ROOM CHANGES TO VACANT:", hideable: false)		{
            input "dimTimer", "number", title: "Checking state timer for how many seconds? (this value should be higher than your motion sensor blind window. recommended value 2 x motion sensor blind window. this also doubles as the dim timer to dim lights for same number of seconds.)", required: false, multiple: false, defaultValue: 5, range: "5..99999", submitOnChange: true
            if (dimTimer)
                input "dimByLevel", "enum", title: "Dim lights by what level?", required: false, multiple: false, defaultValue: null,
                                                    options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"]]
            else
                paragraph "Dim lights by what level?\nselect timer seconds above to set"
        }
	}
}

private pageVacantSettings()      {
	dynamicPage(name: "pageVacantSettings", title: "", install: false, uninstall: false)     {
        section("VACANT SETTINGS:", hideable: false)		{
            if (musicDevice)
                input "turnOffMusic", "bool", title: "Pause speaker when room changes to vacant?", required: false, multiple: false, defaultValue: false
            else
                paragraph "Stop speaker when room changes to vacant?\nselect music player in speaker settings to set"
        }
	}
}

private pageLuxTimeSettings()       {
	dynamicPage(name: "pageLuxTimeSettings", title: "", install: false, uninstall: false)      {
		section("LUX SENSOR FOR USE WITH SETTINGS BELOW AND/OR RULES:", hideable: false)      {
            input "luxSensor", "capability.illuminanceMeasurement", title: "Which lux sensor?", required: false, multiple: false
            /*            if (luxSensor)
                            input "luxThreshold", "number", title: "What lux value?", required: false, multiple: false, defaultValue: null, range: "0..*"
                        else
                            paragraph "What lux value?\nset lux sensor to select"*/
        }
	}
}

private pageSpeakerSettings()       {
	dynamicPage(name: "pageSpeakerSettings", title: "", install: false, uninstall: false)      {
		section("SPEAKER SETTINGS:", hideable: false)      {
            input "musicDevice", "capability.musicPlayer", title: "Which music player?", required: false, multiple: false
        }
	}
}

private pagePowerSettings()       {
	dynamicPage(name: "pagePowerSettings", title: "", install: false, uninstall: false)      {
		section("POWER METER SETTINGS:", hideable: false)      {
            input "powerDevice", "capability.powerMeter", title: "Which power meter?", required: false, multiple: false
        }
	}
}

private pageAutoLevelSettings()     {
    ifDebug("pageAutoLevelSettings")
    def wTime
    def sTime
    if (autoColorTemperature && (wakeupTime || sleepTime))     {
        if (!wakeupTime || !sleepTime)
            sendNotification("Invalid time range!", [method: "push"])
        else        {
            wTime = timeToday(wakeupTime, location.timeZone)
            sTime = timeToday(sleepTime, location.timeZone)
            if (wTime > sTime || ((sTime.getTime() - wTime.getTime()) < 18000L))
                sendNotification("Invalid time range!", [method: "push"])
        }
    }
    updateRulesToState()
    def levelRequired = (autoColorTemperature || state.ruleHasAL || minLevel || maxLevel ? true : false)
	dynamicPage(name: "pageAutoLevelSettings", title: "", install: false, uninstall: false)    {
        section("SETTINGS FOR AUTO LEVEL WHEN RULE LEVEL IS SET TO 'AL':", hideable: false)		{
/*            if    {*/
                input "minLevel", "number", title: "Minimum level?", required: levelRequired, multiple: false, defaultValue: (levelRequired ? 1 : null), range: "1..${maxLevel ?: 100}", submitOnChange: true
                input "maxLevel", "number", title: "Maximum level?", required: levelRequired, multiple: false, defaultValue: (levelRequired ? 100 : null), range: "${minLevel ?: 1}..100", submitOnChange: true
/*            }
            else    {
                input "minLevel", "number", title: "Minimum Level?", required: false, multiple: false, defaultValue: null, range: "1..${maxLevel ?: 100}", submitOnChange: true
                input "maxLevel", "number", title: "Maximum Level?", required: false, multiple: false, defaultValue: null, range: "$minLevel..100", submitOnChange: true
            }*/
        }
        section("SETTINGS FOR AUTO COLOR TEMPERATURE WHEN RULE LEVEL IS SET TO 'AL':", hideable: false)		{
            input "autoColorTemperature", "bool", title: "Auto set color temperature when using 'AL'?", required: false, multiple: false, defaultValue: false, submitOnChange: true
            if (autoColorTemperature)       {
                input "wakeupTime", "time", title: "Wakeup Time?", required: true, multiple: false, submitOnChange: true
                input "sleepTime", "time", title: "Sleep Time?", required: true, multiple: false, submitOnChange: true
                input "minKelvin", "number", title: "Minimum kelvin?", required: true, multiple: false, defaultValue: 1900, range: "1500..${maxKelvin?:9000}", submitOnChange: true
                input "maxKelvin", "number", title: "Maximum kelvin?", required: true, multiple: false, defaultValue: 6500, range: "$minKelvin..9000", submitOnChange: true
            }
            else    {
                paragraph "Wakeup time?\nenable auto color temperature above to set"
                paragraph "Sleep time?\nenable auto color temperature above to set"
                paragraph "Minimum kelvin?\nenable auto color temperature above to set"
                paragraph "Maximum kelvin?\nenable auto color temperature above to set"
            }
        }
    }
}

private pageRules()     {
    updateRulesToState()
    state.passedOn = false
    state.pList = webCoRE_list()
    state.pEnum = [:]
    state.pList.each  { pL ->
        state.pEnum << [(pL.id):(pL.name)]
    }
    if (state.pEnum)    state.pList = state.pEnum.sort { it.value };
    state.pEnum = [:]
	dynamicPage(name: "pageRules", title: "", install: false, uninstall: false)    {
//        state.rules = [1:[ruleNo:1, name:'Rule 1', mode:location.currentMode, state:null, level:50, ct:2700, color:[saturation:80,hue:20]]]
        section()   {
            def emptyRule = null
            if (!state.rules)   {
                emptyRule = 1
            }
            else    {
                def i = 1
                for (; i < 11; i++)     {
                    def ruleNo = String.valueOf(i)
                    def thisRule = getRule(ruleNo, false)
                    if (thisRule)   {
                        def ruleDesc = "$ruleNo:"
                        ruleDesc = (thisRule.mode ? "$ruleDesc Mode=$thisRule.mode" : "$ruleDesc")
                        ruleDesc = (thisRule.state ? "$ruleDesc State=$thisRule.state" : "$ruleDesc")
//                        ruleDesc = (thisRule.luxThreshold != null ? "$ruleDesc Lux=$thisRule.luxThreshold" : (luxThreshold ? "$ruleDesc Lux=$luxThreshold" : "$ruleDesc"))
                        ruleDesc = (thisRule.luxThreshold != null ? "$ruleDesc Lux=$thisRule.luxThreshold" : "$ruleDesc")
                        ruleDesc = (thisRule.piston ? "$ruleDesc Piston=$thisRule.piston" : "$ruleDesc")
                        ruleDesc = (thisRule.actions ? "$ruleDesc Routines=$thisRule.actions" : "$ruleDesc")
                        if (thisRule.fromTimeType && thisRule.toTimeType)        {
                            def ruleFromTimeHHmm = (thisRule.fromTime ? format24hrTime(timeToday(thisRule.fromTime, location.timeZone)) : '')
                            def ruleToTimeHHmm = (thisRule.toTime ? format24hrTime(timeToday(thisRule.toTime, location.timeZone)) : '')
                            ruleDesc = (thisRule.fromTimeType == timeTime() ? "$ruleDesc From=$ruleFromTimeHHmm" : (thisRule.fromTimeType == timeSunrise() ? "$ruleDesc From=Sunrise" : "$ruleDesc From=Sunset"))
                            ruleDesc = (thisRule.toTimeType == timeTime() ? "$ruleDesc To=$ruleToTimeHHmm" : (thisRule.toTimeType == timeSunrise() ? "$ruleDesc To=Sunrise" : "$ruleDesc To=Sunset"))
                        }
                        ruleDesc = (thisRule.switchesOn ? "$ruleDesc ON=$thisRule.switchesOn" : "$ruleDesc")
                        ruleDesc = (thisRule.switchesOff ? "$ruleDesc OFF=$thisRule.switchesOff" : "$ruleDesc")
                        ruleDesc = (thisRule.disabled ? "$ruleDesc Disabled=$thisRule.disabled" : "$ruleDesc")
                        href "pageRule", title: "$ruleDesc", params: [ruleNo: "$ruleNo"], required: false
//                        href "pageRule", description: "$thisRule.name", title: "$ruleDesc", params: [ruleNo: "$ruleNo"], required: false
                    }
                    else
                        if (!emptyRule)
                            emptyRule = i
                }
/*                for (def rule in state.rules.sort{ it.key })    {
                    def thisRule = rule.value
                    ifDebug("$thisRule")
                    href "pageRule", description: "${thisRule.name}", title: "${thisRule.ruleNo}", params: [ruleNo: "${thisRule.ruleNo}"], required: false
                    i++
                }*/
            }
            if (emptyRule)
                href "pageRule", title: "Create new rule", params: [ruleNo: emptyRule], required: false
            else
                paragraph "At max number of rules: 10"
        }
	}
}

private pageRule(params)   {
    if (!state.passedOn && params)      {
        state.passedOn = true
        state.passedParams = params
    }
    if (params.ruleNo)
        state.pageRuleNo = params.ruleNo
    else if (state.passedParams)
        state.pageRuleNo = state.passedParams.ruleNo
    def ruleNo = state.pageRuleNo
    def ruleFromTimeType = settings["fromTimeType$ruleNo"]
    def ruleToTimeType = settings["toTimeType$ruleNo"]
    def ruleFromTimeHHmm = (settings["fromTime$ruleNo"] ? format24hrTime(timeToday(settings["fromTime$ruleNo"], location.timeZone)) : '')
    def ruleToTimeHHmm = (settings["toTime$ruleNo"] ? format24hrTime(timeToday(settings["toTime$ruleNo"], location.timeZone)) : '')
    def allActions = location.helloHome?.getPhrases()*.label
    if (allActions)
        allActions.sort();
    dynamicPage(name: "pageRule", title: "", install: false, uninstall: false)   {
        section()     {
            ifDebug("rule number page ${ruleNo}")
            paragraph "$ruleNo"
			input "name$ruleNo", "text", title: "Rule name?", required:false, multiple: false, capitalization: "none"
            input "disabled$ruleNo", "bool", title: "Rule disabled?", required: false, multiple: false, defaultValue: false
			input "mode$ruleNo", "mode", title: "Which mode?", required: false, multiple: true
            input "state$ruleNo", "enum", title: "Which state?", required: false, multiple: true,
                    options: ['asleep', 'engaged', 'occupied', 'vacant']
            input "dayOfWeek$ruleNo", "enum", title: "Which days of the week?", required: false, multiple: false, defaultValue: null,
                                                options: [[null:"All Days of Week"],[8:"Monday to Friday"],[9:"Saturday & Sunday"],[2:"Monday"],\
                                                          [3:"Tuesday"],[4:"Wednesday"],[5:"Thursday"],[6:"Friday"],[7:"Saturday"],[1:"Sunday"]]

            if (luxSensor)
                input "luxThreshold$ruleNo", "number", title: "What lux value?", required: false, multiple: false, defaultValue: null, range: "0..*"
            else
                paragraph "What lux value?\nset lux sensor in main settings to select."
        }

        section("") {
        	href "pageRuleDate", title: "Date filter", description: "${(settings["fromDate$ruleNo"] || settings["toDate$ruleNo"] ? settings["fromDate$ruleNo"] + ' - ' + settings["toDate$ruleNo"] : 'Add date filtering')}", params: [ruleNo: "$ruleNo"]
        }

        section("") {
        	href "pageRuleTime", title: "Time filter", description: "${(ruleFromTimeType || ruleToTimeType ? (ruleFromTimeType == timeTime() ? "$ruleFromTimeHHmm" : (ruleFromTimeType == timeSunrise() ? "Sunrise" : "Sunset")) + ' - ' + (ruleToTimeType == timeTime() ? "$ruleToTimeHHmm" : (ruleToTimeType == timeSunrise() ? "Sunrise" : "Sunset")) : 'Add time filtering')}", params: [ruleNo: "$ruleNo"]
        }

        section()     {
            input "piston$ruleNo", "enum", title: "Piston to execute?", required: false, multiple: false, defaultValue: null, options: state.pList
            input "actions$ruleNo", "enum", title: "Routines to execute?", required: false, multiple: true, defaultValue: null, options: allActions
            if (musicDevice)
                input "musicAction$ruleNo", "enum", title: "Start or stop music player?", required: false, multiple: false, defaultValue: null,
                                                                                options: [[1:"Start music player"], [2:"Pause music player"], [3:"Neither"]]
            else
                paragraph "Start or stop music player?\nset music player in speaker settings to set."
        }

        section()     {
            input "switchesOn$ruleNo", "capability.switch", title: "Turn ON which switches?", required: false, multiple: true
            input "setLevelTo$ruleNo", "enum", title: "Set level when Turning ON?", required: false, multiple: false, defaultValue: null, submitOnChange: true,
                    options: [[AL:"Auto Level (and color temperature)"],[1:"1%"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]]
            input "setColorTo$ruleNo", "enum", title: "Set color when turning ON?", required: false, multiple:false, defaultValue: null,
                                                                               options: [["Soft White":"Soft White - Default"],
                            					                                         ["White":"White - Concentrate"],
                            					                                         ["Daylight":"Daylight - Energize"],
                            					                                         ["Warm White":"Warm White - Relax"],
                            					                                         "Red","Green","Blue","Yellow","Orange","Purple","Pink"]
            if (settings["setLevelTo$ruleNo"] == 'AL' && autoColorTemperature)
                paragraph "Set color temperature when turning ON? (if light supports color and color is specified this setting will be ignored.)\ncannot set when level is set to 'AL'."
            else
                input "setColorTemperatureTo$ruleNo", "number", title: "Set color temperature when turning ON? (if light supports color and color is specified this setting will be ignored.)",
                                                                                        required: false, multiple: false, defaultValue: null, range: "1500..6500"
            input "switchesOff$ruleNo", "capability.switch", title: "Turn OFF which switches?", required: false, multiple: true
        }
    }
}

private pageRuleDate(params)   {
    if (params.ruleNo)
        state.pageRuleNo = params.ruleNo
    else if (state.passedParams)
            state.pageRuleNo = state.passedParams.ruleNo
    def ruleNo = state.pageRuleNo
    def ruleFromDate = settings["fromDate$ruleNo"]
    def ruleToDate = settings["toDate$ruleNo"]
//    if ((ruleFromDate && ruleToDate) && (!dateInputValid(ruleFromDate, true) || !dateInputValid(ruleToDate, false)))
    if (ruleFromDate && ruleToDate)     {
        def fTime = dateInputValid(ruleFromDate, true)
        def tTime = dateInputValid(ruleToDate, false)
        def fTime2
        def tTime2
        if (fTime && tTime)     {
            fTime2 = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", fTime)
            tTime2 = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", tTime)
        }
        if (!fTime || !tTime || tTime2 < fTime2)
            sendNotification("Invalid date range!", [method: "push"])
    }
    dynamicPage(name: "pageRuleDate", title: "", install: false, uninstall: false)   {
        section     {
            paragraph 'NO WAY TO VALIDATE DATE FORMAT ON INPUT DUE TO ST LIMITATION. IF INVALID DATE IS ENTERED IN EITHER FROM OR TO FEILD DATE CHECKING WILL BE SKIPPED.'
            input "fromDate$ruleNo", "text", title: "From date? (yyyy/MM/dd format)", required: (ruleToDate ? true : false), multiple: false, defaultValue: null, submitOnChange: true
            input "toDate$ruleNo", "text", title: "To date? (yyyy/MM/dd format)", required: (ruleFromDate ? true : false), multiple: false, defaultValue: null, submitOnChange: true
        }
    }
}

private pageRuleTime(params)   {
    if (params.ruleNo)
        state.pageRuleNo = params.ruleNo
    else if (state.passedParams)
            state.pageRuleNo = state.passedParams.ruleNo
    def ruleNo = state.pageRuleNo
    def ruleFromTimeType = settings["fromTimeType$ruleNo"]
    def ruleToTimeType = settings["toTimeType$ruleNo"]
    dynamicPage(name: "pageRuleTime", title: "", install: false, uninstall: false)   {
        section()     {
            if (ruleToTimeType)
                input "fromTimeType$ruleNo", "enum", title: "Choose from time type?", required: true, multiple: false, defaultValue: null, submitOnChange: true, options: [[1:"Sunrise"],[2:"Sunset"],[3:"Time"]]
            else
                input "fromTimeType$ruleNo", "enum", title: "Choose from time type?", required: false, multiple: false, defaultValue: null, submitOnChange: true, options: [[1:"Sunrise"],[2:"Sunset"],[3:"Time"]]
            if (ruleFromTimeType == '3')
                input "fromTime$ruleNo", "time", title: "From time?", required: true, multiple: false, defaultValue: null
            else
                paragraph "From time?\nchange from time type to time to select"
            if (ruleFromTimeType)
                input "toTimeType$ruleNo", "enum", title: "Choose to time type?", required: true, multiple: false, defaultValue: null, submitOnChange: true, options: [[1:"Sunrise"],[2:"Sunset"],[3:"Time"]]
            else
                input "toTimeType$ruleNo", "enum", title: "Choose to time type?", required: false, multiple: false, defaultValue: null, submitOnChange: true, options: [[1:"Sunrise"],[2:"Sunset"],[3:"Time"]]
            if (ruleToTimeType == '3')
                input "toTime$ruleNo", "time", title: "To time?", required: true, multiple: false, defaultValue: null
            else
                paragraph "To time?\nchange to time type to time to select"
        }
    }
}

private dateInputValid(dateInput, isStartDate)       {
    if (!dateInput || dateInput.size() < 8 || dateInput.size() > 10)
        return null
    def dI = Date.parse("yyyy/M/d HH:mm:ss z", dateInput + (isStartDate ? ' 00:00:00 ' : ' 23:59:59 ') + location.timeZone.getDisplayName())
//    ifDebug("$dI")
    def dP = dI.format("yyyy-MM-dd'T'HH:mm:ssZ")
    if (!dP)    return null;
//    ifDebug("$dP")
    return dP
}

private pageNightMode() {
    def buttonNames = [[1:"One"],[2:"Two"],[3:"Three"],[4:"Four"],[5:"Five"],[6:"Six"],[7:"Seven"],[8:"Eight"],[9:"Nine"],[10:"Ten"],[11:"Eleven"],[12:"Twelve"]]
    def nightButtonOptions = [:]
    if (nightButton)      {
        def nightButtonAttributes = nightButton.supportedAttributes
        def attributeNameFound = false
        nightButtonAttributes.each  { att ->
            if (att.name == 'occupancy')
                buttonNames = [[1:'occupied'], [2:'checking'], [3:'vacant'], [4:'locked'], [5:'reserved'], [6:'kaput'], [7:'donotdisturb'], [8:'asleep'], [9:'engaged']]
            if (att.name == 'numberOfButtons')
                attributeNameFound = true
        }
        def numberOfButtons = nightButton.currentValue("numberOfButtons")
        if (attributeNameFound && numberOfButtons)      {
            for (def i = 0; i < numberOfButtons; i++)
                nightButtonOptions << buttonNames[i]
        }
        else
            nightButtonOptions << [null:"No buttons"]
    }
	dynamicPage(name: "pageNightMode", title: "", install: false, uninstall: false) {
        section("SETTINGS FOR 'ASLEEP' STATE INCLUDING SWITCHES TO TURN ON AND OFF, MOTION DETECTED NIGHT LIGHTS AND BUTTON TO TURN ON AND OFF NIGHT LIGHTS.", hideable: false)		{
	    	input "asleepSensor", "capability.sleepSensor", title: "Sleep sensor to change room state to ASLEEP?", required: false, multiple: false
            input "noAsleep", "number", title: "Timeout ASLEEP state after how many hours?", required: false, multiple: false, defaultValue: null, range: "1..99"
            if (motionSensors)
                input "nightSwitches", "capability.switch", title: "Turn ON which Switches when room state is ASLEEP and there is Motion?", required: false, multiple: true, submitOnChange: true
            else
                paragraph "Turn ON which Switches when room state is ASLEEP and there is Motion?\nselect motion sensor(s) above to set."
            if (nightSwitches)      {
                input "nightSetLevelTo", "enum", title: "Set Level When Turning ON?", required: false, multiple: false, defaultValue: null,
                                        options: [[1:"1%"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]]
                input "nightButton", "capability.button", title: "Button to toggle ASLEEP state Switches?", required: false, multiple: false, submitOnChange: true
                if (nightButton)
                    input "nightButtonIs", "enum", title: "Button Number?", required: true, multiple: false, defaultValue: null, options: nightButtonOptions
                else
                    paragraph "Button Number?\nselect button above to set"

                if (nightButton)
                    input "nightButtonAction", "enum", title: "Turn on/off or toggle switches?", required: true, multiple: false, defaultValue: null, submitOnChange: true, options: [[1:"Turn on"],[2:"Turn off"],[3:"Toggle"]]
                else
                    paragraph "Button Action?\nselect action for the button above to set"
            }
            else        {
                paragraph "Set Level When Turning ON?\nselect switches above to set"
                paragraph "Button to toggle Night Switches?\nselect switches rooms above to set"
                paragraph "Button Number?\nselect button above to set"
            }
        }
	}
}

private pageAdjacentRooms() {
	def roomNames = parent.getRoomNames(app.id)
	dynamicPage(name: "pageAdjacentRooms", title: "", install: false, uninstall: false)    {
		section("ADJACENT ROOMS ALLOWS FOR ACTION WHEN THERE IS MOTION IN ADJACENT ROOMS.", hideable: false)		{
            input "adjRooms", "enum", title: "Adjacent Rooms?", required: false, multiple: true, defaultValue: null, options: roomNames, submitOnChange: true
            if (adjRooms)   {
                input "adjRoomsMotion", "bool", title: "If motion in adjacent room check if person is still in this room?", required: false, multiple: false, defaultValue: false
                input "adjRoomsPathway", "bool", title: "If moving through room turn on switches in adjacent rooms?", required: false, multiple: false, defaultValue: false
            }
            else    {
                paragraph "If motion in adjacent room check if person still in this room?\nselect adjacent rooms above to set"
                paragraph "If moving through room turn on switches in adjacent rooms?\nselect adjacent rooms above to set"
            }
        }
	}
}

private pageGeneralSettings() {
	dynamicPage(name: "pageGeneralSettings", title: "", install: false0, uninstall: false) {
		section("MODE SETTINGS FOR AWAY AND PAUSE MODES?", hideable: false)		{
            input "awayModes", "mode", title: "Away modes to set Room to 'VACANT'?", required: false, multiple: true
            input "pauseModes", "mode", title: "Modes in which to pause automation?", required: false, multiple: true
        }
        section("TURN OFF ALL SWITCHES ON NO RULE MATCH?", hideable: false)		{
            input "allSwitchesOff", "bool", title: "Turn OFF all switches?", required: false, multiple: false, defaultValue: false
        }
        section("RUN ROOMS AUTOMATION ON WHICH DAYS OF THE WEEK.\n(WHEN BLANK RUNS ON ALL DAYS.)", hideable: false)		{
            input "dayOfWeek", "enum", title: "Which days of the week?", required: false, multiple: false, defaultValue: null,
						                                                    options: [[null:"All Days of Week"],[8:"Monday to Friday"],[9:"Saturday & Sunday"],[2:"Monday"],\
                                                                                      [3:"Tuesday"],[4:"Wednesday"],[5:"Thursday"],[6:"Friday"],[7:"Saturday"],[1:"Sunday"]]
		}
        section("TEMPERATURE SENSOR", hideable: false)		{
            input "temperatureSensor", "capability.temperatureMeasurement", title: "Which temperature sensor?", required: false, multiple: false
        }
	}
}

private pageAllSettings() {
    ifDebug("pageAllSettings")
    def dOW = [[null:"All Days of Week"],[8:"Monday to Friday"],[9:"Saturday & Sunday"],[2:"Monday"],[3:"Tuesday"],[4:"Wednesday"],[5:"Thursday"],[6:"Friday"],[7:"Saturday"],[1:"Sunday"]]
	dynamicPage(name: "pageAllSettings", title: "", install: false, uninstall: false)    {
		section("", hideable: false)		{
            paragraph "Motion sensor:\t${(motionSensors ? true : '')}\nMotion timeout:\t${(motionSensors ? (noMotion ?: '') : '')}\nMotion event:\t\t${(motionSensors ? (whichNoMotion == 1 ? 'Last Motion Active' : 'Last Motion Inactive') : '')}"
            paragraph "Dim timer:\t\t${(dimTimer ?: '')}\nDim level:\t\t${(dimByLevel ?: '')}"
//            paragraph "Lux sensor:\t\t\t\t${(luxSensor ? true : '')}\nLux threshold:\t\t\t${(luxThreshold ?: '')}\nTurn off last switches:\t$allSwitchesOff"
            paragraph "Lux sensor:\t\t${(luxSensor ? true : '')}"
            paragraph "Power meter:\t\t${(powerDevice ? true : '')}"
            paragraph "Min level:\t\t\t${(minLevel ?: '')}\nMax level:\t\t\t${(maxLevel ?: '')}\nSet kelvin also?\t$autoColorTemperature\nWakeup time:\t\t${(autoColorTemperature ? format24hrTime(timeToday(wakeupTime, location.timeZone)) : '')}\nSleep time:\t\t${(autoColorTemperature ? format24hrTime(timeToday(sleepTime, location.timeZone)) : '')}\nMin kelvin:\t\t\t${(autoColorTemperature ? minKelvin : '')}\nMax kelvin:\t\t${(autoColorTemperature ? maxKelvin : '')}"
            paragraph "Room busy check:\t${(!busyCheck ? 'No traffic check' : (busyCheck == '3' ? 'Light traffic' : (busyCheck == '5' ? 'Medium traffic' : 'Heavy traffic')))}\n\nEngaged button:\t\t${(engagedButton ? true : '')}\nButton number:\t\t${(engagedButton && buttonIs ? buttonIs : '')}\nPerson presence:\t\t${(personsPresence ? personsPresence.size() : '')}\nPresence action:\t\t${(personsPresence ? (presenceAction == '1' ? 'Engaged on arrival' : (presenceAction == '2' ? 'Vacant on Departure' : (presenceAction == 3 ? 'Both' : 'Neither'))) : '')}\nPower meter:\t\t\t${(powerDevice ?: '')}\nPower value:\t\t\t${(powerDevice ? powerValue : '')}\nEngaged switches:\t\t${(engagedSwitch ? engagedSwitch.size() : '')}\nContact sensor:\t\t${(contactSensor ? true : '')}\nEngaged timeout:\t${(noMotionEngaged ?: '')}\nDirect reset:\t\t\t${(resetEngagedDirectly ? true : false)}"
            paragraph "Asleep sensor:\t${(asleepSensor ? true : '')}\nAsleep timeout:\t${(noAsleep ? noAsleep + ' hours' : '')}\n\nAsleep switches:\t${(nightSwitches ? true : '')}\nNight level:\t\t${(nightSetLevelTo ?: '')}\nNight button:\t\t${(nightButton ? true : '')}\nButton number:\t${(!nightButton ? '' : (nightButtonIs ?: ''))}"
            paragraph "Away modes:\t\t\t\t${(awayModes ? awayModes.size() : '')}\nPause modes:\t\t\t\t${(pauseModes ? pauseModes.size() : '')}\nTurn off all switches:\t$allSwitchesOff\nDay of week:\t\t\t${(dayOfWeek ? dOW[dayOfWeek] : 'All days')}"
            def i = 1
            for (; i < 11; i++)     {
                def ruleNo = String.valueOf(i)
                def thisRule = getRule(ruleNo, false)
                if (!thisRule)      continue;
                def ruleDesc = "$ruleNo:"
                ruleDesc = (thisRule.disabled ? "$ruleDesc Disabled=$thisRule.disabled" : "$ruleDesc")
                ruleDesc = (thisRule.mode ? "$ruleDesc Mode=$thisRule.mode" : "$ruleDesc")
                ruleDesc = (thisRule.state ? "$ruleDesc State=$thisRule.state" : "$ruleDesc")
                ruleDesc = (thisRule.dayOfWeek ? "$ruleDesc Days of Week=$thisRule.dayOfWeek" : "$ruleDesc")
                ruleDesc = (thisRule.luxThreshold != null ? "$ruleDesc Lux=$thisRule.luxThreshold" : "$ruleDesc")
                if (thisRule.fromDate && thisRule.toDate)        {
                    def ruleFromDate = dateInputValid(settings["fromDate$ruleNo"], true)
                    def ruleToDate = dateInputValid(settings["toDate$ruleNo"], false)
                    if (ruleFromDate && ruleToDate)     {
                        ruleDesc = (thisRule.fromDate ? "$ruleDesc From=${settings["fromDate$ruleNo"]}" : "$ruleDesc")
                        ruleDesc = (thisRule.toDate ? "$ruleDesc To=${settings["toDate$ruleNo"]}" : "$ruleDesc")
                    }
                }
                if (thisRule.fromTimeType && thisRule.toTimeType)        {
                    def ruleFromTimeHHmm = (thisRule.fromTime ? format24hrTime(timeToday(thisRule.fromTime, location.timeZone)) : '')
                    def ruleToTimeHHmm = (thisRule.toTime ? format24hrTime(timeToday(thisRule.toTime, location.timeZone)) : '')
                    ruleDesc = (thisRule.fromTimeType == timeTime() ? "$ruleDesc From=$ruleFromTimeHHmm" : (thisRule.fromTimeType == timeSunrise() ? "$ruleDesc From=Sunrise" : "$ruleDesc From=Sunset"))
                    ruleDesc = (thisRule.toTimeType == timeTime() ? "$ruleDesc To=$ruleToTimeHHmm" : (thisRule.toTimeType == timeSunrise() ? "$ruleDesc To=Sunrise" : "$ruleDesc To=Sunset"))
                }
                ruleDesc = (thisRule.piston ? "$ruleDesc Piston=true" : "$ruleDesc")
                ruleDesc = (thisRule.actions ? "$ruleDesc Routines=true" : "$ruleDesc")
                ruleDesc = (thisRule.musicAction ? "$ruleDesc Music=${(thisRule.musicAction == '1' ? 'Start' : (thisRule.musicAction == '2' ? 'Pause' : 'Neither'))}" : "$ruleDesc")
                if (thisRule.switchesOn)    {
                    ruleDesc = (thisRule.switchesOn ? "$ruleDesc ON=${thisRule.switchesOn.size()}" : "$ruleDesc")
                    ruleDesc = (thisRule.level ? "$ruleDesc Level=$thisRule.level" : "$ruleDesc")
                    ruleDesc = (thisRule.color ? "$ruleDesc Color=$thisRule.color" : "$ruleDesc")
                    ruleDesc = (thisRule.colorTemperature ? "$ruleDesc Kelvin=$thisRule.colorTemperature" : "$ruleDesc")
                }
                ruleDesc = (thisRule.switchesOff ? "$ruleDesc OFF=${thisRule.switchesOff.size()}" : "$ruleDesc")
                paragraph "$ruleDesc"
            }
        }
    }
}

def installed()		{}

def updated()	{
    if (!childCreated())	{
		spawnChildDevice(app.label)
	}
    if (!(parent.handleAdjRooms()))     {
        ifDebug("no adjacent rooms")
        updateRoom(null)
    }
    def child = getChildDevice(getRoom())
    def adjRoomNames = []
    adjRooms.each  {  adjRoomNames << parent.getARoomName(it)  }
    def busyCheckDisplay = (state.busyCheck == 3 ? ['Light traffic'] : (state.busyCheck == 5 ? ['Medium traffic'] : (state.busyCheck == 7 ? ['Heavy traffic'] : [])))
    def devicesMap = ['busyCheck':busyCheckDisplay, 'engagedButton':engagedButton, 'presence':personsPresence, 'engagedSwitch':engagedSwitch, 'contactSensor':contactSensor,
                      'motionSensors':motionSensors, 'luxSensor':luxSensor, 'adjRoomNames':adjRoomNames,
                      'sleepSensor':asleepSensor, 'nightButton':nightButton, 'nightSwitches':nightSwitches, 'awayModes':awayModes, 'pauseModes':pauseModes]

    child.deviceList(devicesMap)
}

def updateRoom(adjMotionSensors)     {
    ifDebug("updateRoom")
	unsubscribe()
    unschedule()
	initialize()
    state.clear()
    def child = getChildDevice(getRoom())
	subscribe(location, modeEventHandler)
    state.noMotion = ((noMotion && noMotion >= 5) ? noMotion : 0)
    state.noMotionEngaged = ((noMotionEngaged && noMotionEngaged >= 5) ? noMotionEngaged : 0)
	if (motionSensors)	{
    	subscribe(motionSensors, "motion.active", motionActiveEventHandler)
    	subscribe(motionSensors, "motion.inactive", motionInactiveEventHandler)
	}
    if (adjMotionSensors && (adjRoomsMotion || adjRoomsPathway))   {
        subscribe(adjMotionSensors, "motion.active", adjMotionActiveEventHandler)
        subscribe(adjMotionSensors, "motion.inactive", adjMotionInactiveEventHandler)
    }
    state.switchesHasLevel = [:]
    state.switchesHasColor = [:]
    state.switchesHasColorTemperature = [:]
    state.dimTimer = ((dimTimer && dimTimer >= 5) ? dimTimer : 5) // forces minimum of 5 seconds to allow for checking state
    state.dimByLevel = ((state.dimTimer && dimByLevel) ? dimByLevel as Integer : null)
    if (engagedSwitch)      {
    	subscribe(engagedSwitch, "switch.on", engagedSwitchOnEventHandler)
    	subscribe(engagedSwitch, "switch.off", engagedSwitchOffEventHandler)
	}
    if (contactSensor)      {
    	subscribe(contactSensor, "contact.open", contactOpenEventHandler)
    	subscribe(contactSensor, "contact.closed", contactClosedEventHandler)
	}
    if (musicDevice && musicEngaged)       {
        subscribe(musicDevice, "status.playing", musicPlayingEventHandler)
        subscribe(musicDevice, "status.paused", musicStoppedEventHandler)
        subscribe(musicDevice, "status.stopped", musicStoppedEventHandler)
    }
    state.busyCheck = (busyCheck ? busyCheck as Integer : null)
    if (engagedButton)
        subscribe(engagedButton, "button.pushed", buttonPushedEventHandler)
    if (personsPresence)     {
    	subscribe(personsPresence, "presence.present", presencePresentEventHandler)
        subscribe(personsPresence, "presence.not present", presenceNotPresentEventHandler)
    }
    if (anotherRoomEngaged)     {
        parent.subscribeChildrenToEngaged(app.id, anotherRoomEngaged)
//        subsribe(anotherRoomEngaged, "occupancy", anotherRoomEventHandler)
    }
    if (luxSensor)      {
        subscribe(luxSensor, "illuminance", luxEventHandler)
//        state.luxEnabled = true
        state.previousLux = luxSensor.currentValue("illuminance")
    }
    else    {
//        state.luxEnabled = false
        state.previousLux = null
    }
    if (powerDevice)    {
        subscribe(powerDevice, "power", powerEventHandler)
        state.previousPower = powerDevice.currentValue("power")
    }
    else
        state.previousPower = null
    if (asleepSensor)
        subscribe(asleepSensor, "sleeping", sleepEventHandler)
    if (nightButton)
        subscribe(nightButton, "button.pushed", nightButtonPushedEventHandler)
    if (nightSwitches)   {
        nightSwitches.each      {
            if (it.hasCommand("setLevel"))
                state.switchesHasLevel << [(it.getId()):true]
        }
    }
    state.nightSetLevelTo = (nightSetLevelTo ? nightSetLevelTo as Integer : null)
    state.noAsleep = ((noAsleep && noAsleep >= 1) ? (noAsleep * 60 * 60) : 0)
    if (dayOfWeek)      {
        state.dayOfWeek = []
        switch(dayOfWeek)       {
            case '1':   case '2':   case '3':   case '4':   case '5':   case '6':   case '7':
                        state.dayOfWeek << dayOfWeek;                       break;
            case '8':   [1,2,3,4,5].each    { state.dayOfWeek << it };      break;
            case '9':   [6,7].each          { state.dayOfWeek << it };      break;
            default:    state.dayOfWeek = null;                             break;
        }
    }
    else
        state.dayOfWeek = null
    if (temperatureSensor)
        subscribe(temperatureSensor, "temperature", temperatureEventHandler)
    updateRulesToState()
    updateSwitchAttributesToStateAndSubscribe()
    turnOnAndOffSwitches()
    runIn(1, scheduleFromToTimes)
    runIn(3, updateIndicators)
}

def updateIndicators()      {
    ifDebug("updateIndicators")
    def child = getChildDevice(getRoom())
    def devValue
    def ind
    if (motionSensors)      {
        devValue = motionSensors.currentValue("motion");
        if (devValue.contains('active'))    ind = 1;
        else                                ind = 0;
    }
    else
        ind = -1;
    child.updateMotionInd(ind)
    def lux = -1
    if (luxSensor)      lux = getIntfromStr((String) luxSensor.currentValue("illuminance"));
    child.updateLuxInd(lux)
    if (contactSensor)      {
        devValue = contactSensor.currentValue("contact");
        if (devValue.contains('closed'))    ind = 1;
        else                                ind = 0;
    }
    else
        ind = -1
    child.updateContactInd(ind)
    child.updateSwitchInd(isAnySwitchOn())
    if (personsPresence)      {
        devValue = personsPresence.currentValue("presence");
        if (devValue.contains('present'))   ind = 1;
        else                                ind = 0;
    }
    else
        ind = -1;
    child.updatePresenceInd(ind)
    def temp = -1
    if (temperatureSensor)
        temp = temperatureSensor.currentValue("temperature");
    child.updateTemperatureInd(temp)
}

private isAnySwitchOn()   {
    ifDebug("isAnySwitchOn")
    def ind = -1
    for (def i = 1; i < 11; i++)      {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (thisRule && !thisRule.disabled && thisRule.switchesOn)      {
            def devValue = thisRule.switchesOn.currentValue("switch")
            if (devValue.contains('on'))    {
                ind = 1;
                break;
            }
            else
                ind = 0;
        }
    }
    return ind
}

def updateRulesToState()    {
    ifDebug("updateRulesToState")
    state.timeCheck = false
    state.ruleHasAL = false
    state.vacant = false
    state.previousRuleNo = null
    state.rules = false
//    state.rules = [1:[ruleNo:1, name:'Rule 1', mode:location.currentMode, state:null, level:50, ct:2700, color:[saturation:80,hue:20]]]
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
/*        def ruleName = settings["name$ruleNo"]
        def ruleDisabled = settings["disabled$ruleNo"]
        def ruleMode = settings["mode$ruleNo"]
//        def ruleModeID = (ruleMode ? String.valueOf(ruleMode) : null)
        def ruleState = settings["state$ruleNo"]
        def ruleDayOfWeek = []
        if (settings["dayOfWeek$ruleNo"])      {
            switch(settings["dayOfWeek$ruleNo"])       {
                case '1':   case '2':   case '3':   case '4':   case '5':   case '6':   case '7':
                        ruleDayOfWeek << settings["dayOfWeek$ruleNo"];        break;
                case '8':   [1,2,3,4,5].each    { ruleDayOfWeek << it };      break;
                case '9':   [6,7].each          { ruleDayOfWeek << it };      break;
                default:    ruleDayOfWeek = null;                             break;
            }
        }
        else
            ruleDayOfWeek = null
//        def ruleLuxSensorID = null
//        settings["luxSensor$ruleNo"].each { ruleLuxSensorID = it.getId() }
        def ruleLuxThreshold = settings["luxThreshold$ruleNo"]
        def ruleFromTimeType = settings["fromTimeType$ruleNo"]
        def ruleFromTime = settings["fromTime$ruleNo"]
        def ruleToTimeType = settings["toTimeType$ruleNo"]
        def ruleToTime = settings["toTime$ruleNo"]
        def ruleSwitchesOn = settings["switchesOn$ruleNo"]
        def ruleSetLevelTo = settings["setLevelTo$ruleNo"]
        def ruleSetColorTo = settings["setColorTo$ruleNo"]
        def ruleSetHueTo = returnHueAndSaturation(ruleSetColorTo)
        def ruleSetColorTemperatureTo = settings["setColorTemperatureTo$ruleNo"]
        def ruleSwitchesOff = settings["switchesOff$ruleNo"]
//        ifDebug("$ruleNo || $ruleName || $ruleMode || $ruleState || $ruleLuxSensor || $ruleFromTimeType || $ruleToTimeType")
//        ifDebug("$ruleNo || $ruleSetLevelTo || $ruleSetHueTo || $ruleSetColorTemperatureTo")
//        if ((ruleModeID || ruleState || ruleLuxSensorID || ruleFromTimeType || ruleToTimeType) && (ruleSetLevelTo || ruleSetHueTo || ruleSetColorTemperatureTo))       {
//            state.rules << ["$ruleNo":[ruleNo:i, name:ruleName, mode:ruleModeID, state:ruleState, luxSensor:ruleLuxSensorID, luxThreshold:ruleLuxThreshold,
//                               fromTimeType:ruleFromTimeType, fromTime:ruleFromTime, toTimeType:ruleToTimeType, toTime:ruleToTime,
//                               level:ruleSetLevelTo, color:ruleSetHueTo, colorTemperature:ruleSetColorTemperatureTo]]
        ifDebug("$ruleName || $ruleMode || $ruleState || $ruleLuxThreshold || $ruleFromTimeType || $ruleToTimeType")*/
        def thisRule = getRule(ruleNo, false)
        if (thisRule && !thisRule.disabled)     {
//            state.rules << ["$ruleNo":[ruleNo:i, name:ruleName, disabled:ruleDisabled, mode:ruleMode, state:ruleState, dayOfWeek:ruleDayOfWeek, luxThreshold:ruleLuxThreshold,
//                                       fromTimeType:ruleFromTimeType, fromTime:ruleFromTime, toTimeType:ruleToTimeType, toTime:ruleToTime,
//                                       level:ruleSetLevelTo, color:ruleSetColorTo, hue:ruleSetHueTo, colorTemperature:ruleSetColorTemperatureTo]]
            if (!state.rules)   state.rules = [:];
            state.rules << ["$ruleNo":[isRule:true]]
//            if (thisRule.luxThreshold)      state.luxCheck = true
//            state.rules = true
            if (thisRule.level == 'AL')     state.ruleHasAL = true
            if (thisRule.state && thisRule.state.contains('vacant'))    state.vacant = true
            if (thisRule.fromTimeType && thisRule.toTimeType)           state.timeCheck = true
        }
    }
//    state.rules = rulesMap
}

def updateSwitchAttributesToStateAndSubscribe()    {
    ifDebug("updateSwitchAttributesToStateAndSubscribe")
    def switches = []
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo, false)
        if (thisRule && !thisRule.disabled && thisRule.switchesOn)      {
            thisRule.switchesOn.each      {
                switches << it
                def itID = it.getId()
                if (it.hasCommand("setLevel"))
                    state.switchesHasLevel << ["$itID":true]
                if (it.hasCommand("setColor"))
                    state.switchesHasColor << ["$itID":true]
                if (it.hasCommand("setColorTemperature"))
                    state.switchesHasColorTemperature << ["$itID":true]
            }
        }
    }
    if (switches)       {
        subscribe(switches, "switch.on", switchOnEventHandler)
        subscribe(switches, "switch.off", switchOffEventHandler)
    }
}

private getRule(ruleNo, checkState = true)     {
    state.thisRule = [:]
    if (!ruleNo)        return null
    if (checkState && state.rules)      {
        def ruleThere = state.rules[ruleNo]
        if (!ruleThere)      return null
    }
    def ruleName = settings["name$ruleNo"]
    def ruleDisabled = settings["disabled$ruleNo"]
    def ruleMode = settings["mode$ruleNo"]
//        def ruleModeID = (ruleMode ? String.valueOf(ruleMode) : null)
    def ruleState = settings["state$ruleNo"]
    def ruleDayOfWeek = []
    if (settings["dayOfWeek$ruleNo"])      {
        switch(settings["dayOfWeek$ruleNo"])       {
            case '1':   case '2':   case '3':   case '4':   case '5':   case '6':   case '7':
                    ruleDayOfWeek << settings["dayOfWeek$ruleNo"];        break;
            case '8':   [1,2,3,4,5].each    { ruleDayOfWeek << it };      break;
            case '9':   [6,7].each          { ruleDayOfWeek << it };      break;
            default:    ruleDayOfWeek = null;                             break;
        }
    }
    else
        ruleDayOfWeek = null
    def ruleLuxThreshold = settings["luxThreshold$ruleNo"]
/*    if (checkState && ruleLuxThreshold != null)       {
        if (luxThreshold)   {
            if (Integer.valueOf(ruleLuxThreshold) > Integer.valueOf(luxThreshold))
                ruleLuxThreshold = Integer.valueOf(luxThreshold)
        }
//        else
//            ruleLuxThreshold = null
    }*/
    def ruleFromDate = dateInputValid(settings["fromDate$ruleNo"], true)
    def ruleToDate = dateInputValid(settings["toDate$ruleNo"], false)
    def ruleFromTimeType = settings["fromTimeType$ruleNo"]
    def ruleFromTime = settings["fromTime$ruleNo"]
    def ruleToTimeType = settings["toTimeType$ruleNo"]
    def ruleToTime = settings["toTime$ruleNo"]
    def rulePiston = settings["piston$ruleNo"]
    def ruleActions = settings["actions$ruleNo"]
    def ruleMusicAction = settings["musicAction$ruleNo"]
    def ruleSwitchesOn = settings["switchesOn$ruleNo"]
//    def ruleSetLevelTo = (settings["setLevelTo$ruleNo"] ? settings["setLevelTo$ruleNo"] as Integer : null)
    def ruleSetLevelTo = settings["setLevelTo$ruleNo"]
    def ruleSetColorTo = settings["setColorTo$ruleNo"]
    def ruleSetHueTo = returnHueAndSaturation(ruleSetColorTo)
    def ruleSetColorTemperatureTo = settings["setColorTemperatureTo$ruleNo"]
    def ruleSwitchesOff = settings["switchesOff$ruleNo"]
    if (ruleName || ruleDisabled || ruleMode || ruleState || ruleDayOfWeek || ruleLuxThreshold != null ||
                    rulePiston || ruleActions || ruleMusicAction || ruleFromDate || ruleToDate || ruleFromTimeType || ruleToTimeType ||
                    ruleSwitchesOn || ruleSetLevelTo || ruleSetColorTo || ruleSetColorTemperatureTo || ruleSwitchesOff)
        return [ruleNo:ruleNo, name:ruleName, disabled:ruleDisabled, mode:ruleMode, state:ruleState, dayOfWeek:ruleDayOfWeek, luxThreshold:ruleLuxThreshold,
                               fromDate:ruleFromDate, toDate:ruleToDate,
                               fromTimeType:ruleFromTimeType, fromTime:ruleFromTime, toTimeType:ruleToTimeType, toTime:ruleToTime,
                               piston:rulePiston, actions:ruleActions, musicAction:ruleMusicAction,
                               switchesOn:ruleSwitchesOn, level:ruleSetLevelTo, color:ruleSetColorTo, hue:ruleSetHueTo, colorTemperature:ruleSetColorTemperatureTo,
                               switchesOff:ruleSwitchesOff]
    else
        return null
}

def	initialize()	{  webCoRE_init()  }

def	modeEventHandler(evt)	{
    ifDebug("modeEventHandler")
    if (state.dayOfWeek && !(checkRunDay()))
        return
	if (awayModes && awayModes.contains(evt.value))    {
    	roomVacant(true)
        return
    }
    else    {
        if (pauseModes && pauseModes.contains(evt.value))   {
            unscheduleAll("mode handler")
            return
        }
    }
    turnOnAndOffSwitches()
}

def	motionActiveEventHandler(evt)	{
    ifDebug("motionActiveEventHandler")
    def child = getChildDevice(getRoom())
    child.updateMotionInd(1)
    if (pauseModes && pauseModes.contains(location.currentMode))        return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
	def roomState = child.getRoomState()
    if (roomState == 'asleep')		{
        if (nightSwitches)      {
            dimNightLights()
            if (state.noMotion && whichNoMotion != lastMotionInactive())    {
                updateChildTimer(state.noMotion)
                runIn(state.noMotion, nightSwitchesOff)
            }
        }
		return
    }
    unscheduleAll("motion active handler")
    if (roomState == 'engaged')     {
        if (state.noMotionEngaged)      {
            updateChildTimer(state.noMotionEngaged)
            runIn(state.noMotionEngaged, roomVacant)
        }
        return
    }
    if (contactSensor && contactSensor.currentValue("contact") == 'closed')     {
        if (['occupied', 'checking'].contains(roomState))
            child.generateEvent('engaged')
        else    {
            if (roomState == 'vacant')
                child.generateEvent('occupied')
        }
    }
    else    {
        if (['checking', 'vacant'].contains(roomState))     {
            if (state.isBusy)       {
                turnOffIsBusy()
                child.generateEvent('engaged')
            }
            else
                child.generateEvent('occupied')
        }
        else    {
            if (roomState == 'occupied' && whichNoMotion == lastMotionActive() && state.noMotion)   {
                updateChildTimer(state.noMotion)
                runIn(state.noMotion, roomVacant)
            }
        }
    }
}

def	motionInactiveEventHandler(evt)     {
    ifDebug("motionInactiveEventHandler")
    def child = getChildDevice(getRoom())
    child.updateMotionInd(0)
    if (pauseModes && pauseModes.contains(location.currentMode))        return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
	def roomState = child.getRoomState()
    if (['occupied'].contains(roomState))       {
//        if (!(state.noMotion))
//            runIn(1, roomVacant)
//        else
            if (state.noMotion && whichNoMotion == lastMotionInactive())    {
                updateChildTimer(state.noMotion)
                runIn(state.noMotion, roomVacant)
            }
    }
    else    {
        if (roomState == 'asleep' && nightSwitches)     {
            if (whichNoMotion == lastMotionInactive())      {
                if (state.noMotion)
                    updateChildTimer(state.noMotion);
                runIn((state.noMotion ?: 1), nightSwitchesOff)
            }
        }
    }
}

def adjMotionActiveEventHandler(evt)    {
    ifDebug("adjMotionActiveEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))    return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (adjRoomsMotion && roomState == 'occupied')      {
        def motionValue = motionSensors.currentValue("motion")
        def motionLastActivity = motionSensors.getLastActivity()
        def mV = motionValue.contains('active')
        def mD = motionLastActivity.max()
        if (mV && mD > evt.date)
            return
        child.generateEvent('checking')
        return
    }
    if (adjRoomsPathway && roomState == 'vacant')       {
        adjRooms.each       {
            def lastStateDate = parent.getLastStateDate(it)
            if (lastStateDate['state'])         {
                def evtDate = evt.date.getTime()
                def lsDate = lastStateDate['date']
                def dateDiff = (evtDate - lsDate) + 0
                if (lastStateDate['state'] == 'vacant')    {
//                    switchesOn()
                    switches2On('occupied')
                    child.generateEvent('checking')
                    return
                }
            }
        }
    }
}

def adjMotionInactiveEventHandler(evt)      {}

def	switchOnEventHandler(evt)       {
    ifDebug("switchOnEventHandler")
    def child = getChildDevice(getRoom())
    child.updateSwitchInd(1)
    if (pauseModes && pauseModes.contains(location.currentMode))        return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
/*    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (roomState == 'vacant')      {
        if (state.noMotion)
            runIn(state.noMotion, dimLights)
    }*/
}

def	switchOffEventHandler(evt)      {
    ifDebug("switchOffEventHandler")
    def child = getChildDevice(getRoom())
    child.updateSwitchInd(isAnySwitchOn())
    if (pauseModes && pauseModes.contains(location.currentMode))        return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
//    if (!('on' in switches2.currentValue("switch")))
//        unschedule()
}

def	buttonPushedEventHandler(evt)     {
    ifDebug("buttonPushedEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (!evt.data)      return;
    def eD = new groovy.json.JsonSlurper().parseText(evt.data)
    assert eD instanceof Map
    if (!eD || (buttonIs && eD['buttonNumber'] != buttonIs as Integer))
    	return
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (roomState == 'engaged')     {
        if (resetEngagedDirectly)
            child.generateEvent('vacant')
        else
            child.generateEvent('checking')
    }
    else
        child.generateEvent('engaged')
}

def	anotherRoomEngagedEventHandler()     {
    ifDebug("anotherRoomEngagedEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
/*    if (!evt.data)      return;
    def aRD = new groovy.json.JsonSlurper().parseText(evt.data)
    assert aRD instanceof Map
    if (!aRD || aRD['buttonNumber'] != 9)
    	return
    ifDebug("anotherRoomEngagedEventHandler button match")*/
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (roomState == 'engaged')     {
        if (resetEngagedDirectly)
            child.generateEvent('vacant')
        else
            child.generateEvent('checking')
    }
}

def	presencePresentEventHandler(evt)     {
    ifDebug("presencePresentEventHandler")
    def child = getChildDevice(getRoom())
    child.updatePresenceInd(1)
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (presenceActionArrival())      {
        def roomState = child.getRoomState()
        if (['occupied', 'checking', 'vacant'].contains(roomState))
            child.generateEvent('engaged')
    }
}

def	presenceNotPresentEventHandler(evt)     {
    ifDebug("presenceNotPresentEventHandler")
    if (personsPresence.currentValue("presence").contains('present'))     return;
    def child = getChildDevice(getRoom())
    child.updatePresenceInd(0)
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (presenceActionDeparture())      {
        def roomState = child.getRoomState()
        if (['asleep', 'engaged', 'occupied'].contains(roomState))      {
            if (resetEngagedDirectly)
                child.generateEvent('vacant')
            else
                child.generateEvent('checking')
        }
    }
}

def	engagedSwitchOnEventHandler(evt)     {
    ifDebug("engagedSwitchOnEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    def child = getChildDevice(getRoom())
	def roomState = child.getRoomState()
    if (['occupied', 'checking', 'vacant'].contains(roomState))
        child.generateEvent('engaged')
}

def	engagedSwitchOffEventHandler(evt)	{
    ifDebug("engagedSwitchOffEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (musicDevice && musicEngaged && musicDevice.currentValue("status") == 'playing')  return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    if (engagedSwitch.currentValue("switch").contains('on'))        return;
    def child = getChildDevice(getRoom())
	def roomState = child.getRoomState()
    if (resetEngagedDirectly && roomState == 'engaged')
        child.generateEvent('vacant')
    else    {
        if (['engaged', 'occupied'].contains(roomState))
            child.generateEvent('checking')
    }
}

def	contactOpenEventHandler(evt)	{
    ifDebug("contactOpenEventHandler")
    def child = getChildDevice(getRoom())
    child.updateContactInd(0)
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (musicDevice && musicEngaged && musicDevice.currentValue("status") == 'playing')  return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    if (engagedSwitch && engagedSwitch.currentValue("switch").contains('on'))  return;
	def roomState = child.getRoomState()
    if (resetEngagedDirectly && roomState == 'engaged')
        child.generateEvent('vacant')
    else    {
        if (['engaged', 'occupied', 'vacant'].contains(roomState))
            child.generateEvent('checking')
    }
}

def	contactClosedEventHandler(evt)     {
    ifDebug("contactClosedEventHandler")
    def child = getChildDevice(getRoom())
    child.updateContactInd(1)
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (musicDevice && musicEngaged && musicDevice.currentValue("status") == 'playing')  return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    if (engagedSwitch && engagedSwitch.currentValue("switch").contains('on'))      return;
    def roomState = child.getRoomState()
//    if (['occupied', 'checking'].contains(roomState) || (!motionSensors && roomState == 'vacant'))
    if (roomState == 'occupied' || (!motionSensors && roomState == 'vacant'))
        child.generateEvent('engaged')
    else    {
        if (motionSensors && roomState == 'vacant')
            child.generateEvent('checking')
    }
}

def musicPlayingEventHandler(evt)       {
    ifDebug("evt.name: $evt.name | evt.value: $evt.value")
    ifDebug("musicPlayingEventHandler")
    def child = getChildDevice(getRoom())
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    if (engagedSwitch && engagedSwitch.currentValue("switch").contains('on'))      return;
    def roomState = child.getRoomState()
//    if (['occupied', 'checking'].contains(roomState) || (!motionSensors && roomState == 'vacant'))
    if (roomState == 'occupied' || (!motionSensors && roomState == 'vacant'))
        child.generateEvent('engaged')
    else    {
        if (motionSensors && roomState == 'vacant')
            child.generateEvent('checking')
    }
}

def musicStoppedEventHandler(evt)       {
    ifDebug("evt.name: $evt.name | evt.value: $evt.value")
    ifDebug("musicStoppedEventHandler")
    def child = getChildDevice(getRoom())
    if (pauseModes && pauseModes.contains(location.currentMode))   return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (powerDevice && powerDevice.currentValue("power") >= powerValue)     return;
    if (engagedSwitch && engagedSwitch.currentValue("switch").contains('on'))  return;
	def roomState = child.getRoomState()
    if (resetEngagedDirectly && roomState == 'engaged')
        child.generateEvent('vacant')
    else    {
        if (['engaged', 'occupied', 'vacant'].contains(roomState))
            child.generateEvent('checking')
    }
}

def temperatureEventHandler(evt)    {
    ifDebug("temperatureEventHandler")
    def child = getChildDevice(getRoom())
    child.updateTemperatureInd(evt.value)
}

def luxEventHandler(evt)    {
    ifDebug("luxEventHandler")
    def child = getChildDevice(getRoom())
    def currentLux = getIntfromStr((String) evt.value)
    child.updateLuxInd(currentLux)
    if (pauseModes && pauseModes.contains(location.currentMode))       return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
//    if (!luxThreshold)      return;
/*    def currentMode = String.valueOf(location.currentMode)
    def child = getChildDevice(getRoom())
    def nowTime	= now()
    def nowDate = new Date(nowTime)
    def sunriseAndSunset = getSunriseAndSunset()
    def sunriseTime = new Date(sunriseAndSunset.sunrise.getTime())
    def sunsetTime = new Date(sunriseAndSunset.sunset.getTime())
//    for (def rule in state.rules.sort{ it.key })    {
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (!thisRule || thisRule.disabled)      continue;
        if (!thisRule.mode && !thisRule.state && !thisRule.dayOfWeek && !thisRule.luxThreshold && !thisRule.fromTimeType && !thisRule.toTimeType)
            continue
        if (thisRule.mode && !thisRule.mode.contains(currentMode))      continue;
        if (thisRule.state && !thisRule.state.contains(roomState))      continue;
        if (thisRule.dayOfWeek && !(checkRunDay(thisRule.dayOfWeek)))    return;
        if (!thisRule.luxThreshold)     continue;
        if ((thisRule.fromTimeType && (thisRule.fromTimeType != timeTime() || thisRule.fromTime)) &&
            (thisRule.toTimeType && (thisRule.toTimeType != timeTime() || thisRule.toTime)))    {
            def fTime = ( thisRule.fromTimeType == timeSunrise() ? sunriseTime : ( thisRule.fromTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.fromTime, location.timeZone)))
            def tTime = ( thisRule.toTimeType == timeSunrise() ? sunriseTime : ( thisRule.toTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.toTime, location.timeZone)))
            if (timeOfDayIsBetween(fTime, tTime, nowDate, location.timeZone))       continue;
        }
        break
    }
    if (i < 11)   {*/
/*    def roomState = child.currentValue("occupancy")
    ifDebug("currentLux: $currentLux | luxThreshold: $luxThreshold | previousLux: $state.previousLux")
    ifDebug("luxFell: ${luxFell(currentLux, luxThreshold)} | luxRose: ${luxRose(currentLux, luxThreshold)}")
    if (luxThreshold)   {
        if (luxFell(currentLux, luxThreshold))    {
            if (['engaged', 'occupied', 'asleep', 'vacant'].contains(roomState))
                switches2On()
        }
        else    {
            if (luxRose(currentLux, luxThreshold))    {
                if (['engaged', 'occupied', 'asleep', 'vacant'].contains(roomState) && allSwitchesOff)
                    switches2Off()
            }
        }
    }
    else*/
    turnOnAndOffSwitches()
    state.previousLux = currentLux
}

private getIntfromStr(String mayOrMayNotBeDecimal)     {
    ifDebug("getIntfromStr - mayOrMayNotBeDecimal: $mayOrMayNotBeDecimal | indexOf: ${mayOrMayNotBeDecimal.indexOf('.')}")
    int intValue
    if (mayOrMayNotBeDecimal.indexOf('.') >= 0)     {
        def str = mayOrMayNotBeDecimal.substring(0, mayOrMayNotBeDecimal.indexOf('.'))
        ifDebug("str: $str")
        intValue = str as Integer
    }
    else
        intValue = mayOrMayNotBeDecimal.toInteger()
    ifDebug("intValue: $intValue")
    return intValue
}

def powerEventHandler(evt)    {
    ifDebug("powerEventHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))       return;
    if (state.dayOfWeek && !(checkRunDay()))    return;
    if (personsPresence && personsPresence.currentValue("presence").contains('present'))     return;
    if (engagedSwitch && engagedSwitch.currentValue("switch").contains('on'))  return;
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    def currentPower = evt.value.toInteger()
    if (powerValue)
        if (currentPower >= powerValue && state.previousPower < powerValue)        {
            if (['occupied', 'checking', 'vacant'].contains(roomState))
                child.generateEvent('engaged')
        }
        else    {
            if (currentPower < powerValue && state.previousPower >= powerValue)        {
                if (roomState == 'engaged')
                    child.generateEvent('checking')
            }
        }
    state.previousPower = currentPower
}

//private luxFell(currentLux, luxThreshold)   {   return (currentLux <= luxThreshold && state.previousLux > luxThreshold)  }

//private luxRose(currentLux, luxThreshold)   {   return (currentLux > luxThreshold && state.previousLux <= luxThreshold)  }

// pass in child and roomState???
def roomVacant(forceVacant = false)	  {
    ifDebug("roomVacant")

    def child = getChildDevice(getRoom())
	def roomState = child.getRoomState()
    if (!forceVacant && motionSensors && ['engaged', 'occupied', 'checking'].contains(roomState))      {
        def motionValue = motionSensors.currentValue("motion")
        if (motionValue.contains('active'))     {
            motionActiveEventHandler(null)
            return
        }
    }

    def newState = null
    if (['engaged', 'occupied'].contains(roomState))    {
        if (state.dimTimer)     newState = 'checking';
        else                    newState = 'vacant';
    }
    else    {
        if (roomState == 'checking')
            newState = 'vacant';
    }
    if (newState)
        child.generateEvent(newState);
}

def roomAwake()	  {
    ifDebug("roomAwake")
	def child = getChildDevice(getRoom())
	def roomState = child.getRoomState()
    def newState = null
    if (roomState == 'asleep')      {
        if (state.dimTimer)     newState = 'checking';
        else                    newState = 'vacant';
    }
    if (newState)
        child.generateEvent(newState)
}

def handleSwitches(oldState = null, newState = null)	{
    ifDebug("${app.label} room state - old: ${oldState} new: ${newState}")
//    state.roomState = newState
//      "yyyy-MM-dd'T'HH:mm:ssZ" = 2017-11-13T23:32:45+0000
    if (!newState || oldState == newState)      return false;
    def nowDate = now()
    state.previousState = ['state':newState, 'date':nowDate]
    previousStateStack(state.previousState)
    if (pauseModes && pauseModes.contains(location.currentMode))       return false;
    if (state.dayOfWeek && !(checkRunDay()))        return false;
    if (oldState == 'asleep')       {
        unschedule('roomAwake')
        updateAsleepChildTimer(0)
        nightSwitchesOff()
    }
    else    {
        unscheduleAll("handle switches")
        if (oldState == 'checking')
            unDimLights()
    }
    def child = getChildDevice(getRoom())
    if (['engaged', 'occupied', 'asleep', 'vacant'].contains(newState))     {
        if (newState != 'vacant' || state.vacant)   // not vacant or has vacant rule
            switches2On()
        else        {
            switches2Off()
//            ifDebug("turnOffMusic: $turnOffMusic | musicDevice.currentStatus: $musicDevice.currentStatus")
            if (musicDevice && turnOffMusic && musicDevice.currentStatus == 'playing')
                musicDevice.pause()
        }
        if (['engaged', 'asleep'].contains(newState))       {
            ifDebug("calling parent.notifyAnotherRoomEngaged: $app.id")
            parent.notifyAnotherRoomEngaged(app.id)
            if (newState == 'asleep')   {
                nightSwitchesOff()
                if (state.noAsleep)     {
                    updateAsleepChildTimer(state.noAsleep)
                    runIn(state.noAsleep, roomAwake)
                }
            }
            else    {
                if (state.noMotionEngaged)      {
                    updateChildTimer(state.noMotionEngaged)
                    runIn(state.noMotionEngaged, roomVacant)
                }
            }
        }
        else    {
            if (newState == 'occupied')     {
                if (state.noMotion)     {
                    def motionValue = motionSensors.currentValue("motion")
                    def mV = motionValue.contains('active')
                    if (whichNoMotion == lastMotionActive() || (whichNoMotion == lastMotionInactive() && !mV))      {
                        updateChildTimer(state.noMotion)
                        runIn(state.noMotion, roomVacant)
                    }
                }
            }
        }
    }
    else
        if (newState == 'checking')     {
            dimLights()
            def dT = state.dimTimer ?: 1
            if (dT > 5)
                updateChildTimer(dT)
            runIn(dT, roomVacant)
        }
}

/*
private switchesOn()	{
    if (fromTimeType && toTimeType)     {
        def nowTime	= now()
        def nowDate = new Date(nowTime)
        def sunriseAndSunset = getSunriseAndSunset()
        def sunriseTime = new Date(sunriseAndSunset.sunrise.getTime())
        def sunsetTime = new Date(sunriseAndSunset.sunset.getTime())
        def fromDate = timeToday(fromTime, location.timeZone)
        def toDate = timeToday(toTime, location.timeZone)
        def fTime = (fromTimeType == timeSunrise() ? sunriseTime : ( fromTimeType == timeSunset() ? sunsetTime : fromDate))
        def tTime = (toTimeType == timeSunrise() ? sunriseTime : ( toTimeType == timeSunset() ? sunsetTime : toDate))
//log.debug "checking time from: $fTime | to: $tTime | date: $nowDate | ${timeOfDayIsBetween(fTime, tTime, nowDate, location.timeZone)}"
        if (!(timeOfDayIsBetween(fTime, tTime, nowDate, location.timeZone)))
            return
    }
    if (state.luxEnabled && luxSensor.currentValue("illuminance") > luxThreshold)
        return
	if (switches)
        switches.each      {
            it.on()
            if (state.setColorTo && state.switchesHasColor[it.getId()])
                it.setColor(state.setColorTo)
            else    {
                if (state.setColorTemperatureTo && state.switchesHasColorTemperature[it.getId()])
                    it.setColorTemperature(state.setColorTemperatureTo)
            }
            if (state.setLevelTo && state.switchesHasLevel[it.getId()])
                it.setLevel(state.setLevelTo)
        }
    if (switches)
        switches2On()
}
*/

def turnOnAndOffSwitches()      {
    ifDebug("turnOnAndOffSwitches")
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (['engaged', 'occupied', 'asleep', 'vacant'].contains(roomState))      {
        def turnedOn = switches2On(roomState)
        if (!turnedOn && allSwitchesOff)        {
            switches2Off()
            if (musicDevice && turnOffMusic && musicDevice.currentStatus == 'playing')
                musicDevice.stop()
        }
    }
}

private switches2On(roomState = null)     {
    ifDebug("switches2On")
/*    if (luxThreshold)     {
        def lux = luxSensor.currentValue("illuminance")
        if (lux > luxThreshold)     return false;
    }*/
    def turnOn = null
    def previousRuleLux = null
    def thisRule = [:]
    if (state.rules)    {
        def currentMode = String.valueOf(location.currentMode)
        if (!roomState)     {
            def child = getChildDevice(getRoom())
            roomState = child.currentValue("occupancy")
        }
        def nowTime	= now() + 1000
        def nowDate = new Date(nowTime)
        def sunriseAndSunset = getSunriseAndSunset()
        def sunriseTime = new Date(sunriseAndSunset.sunrise.getTime())
        def sunsetTime = new Date(sunriseAndSunset.sunset.getTime())
        def i = 1
        for (; i < 11; i++)      {
//        for (def rule in state.rules.sort{ it.key })    {
            def ruleNo = String.valueOf(i)
            thisRule = getRule(ruleNo)
            if (!thisRule || thisRule.disabled)      continue;
//            if (!thisRule.mode && !thisRule.state && !thisRule.dayOfWeek && thisRule.luxThreshold == null && !thisRule.actions &&
//                !thisRule.fromTimeType && !thisRule.toTimeType && !thisRule.switchesOn && !thisRule.switchesOff && !thisRule.piston)
//                continue
            if (thisRule.mode && !thisRule.mode.contains(currentMode))      continue;
            if (thisRule.state && !thisRule.state.contains(roomState))      continue;
            if (thisRule.dayOfWeek && !(checkRunDay(thisRule.dayOfWeek)))   continue;
            if (thisRule.fromDate && thisRule.toDate)   {
                def fTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.fromDate)
                def tTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.toDate)
//                ifDebug("fTime: $fTime | tTime: $tTime | nowDate: $nowDate | is time of day: ${(nowDate < fTime || nowDate > tTime)}")
                if (nowDate < fTime || nowDate > tTime)    continue;
            }
            if (thisRule.luxThreshold != null)   {
                def lux = getIntfromStr((String) luxSensor.currentValue("illuminance"))
                ifDebug("lux from device: $lux | rule lux threshold: $thisRule.luxThreshold")
                if (lux > thisRule.luxThreshold)    continue;
            }
            if ((thisRule.fromTimeType && (thisRule.fromTimeType != timeTime() || thisRule.fromTime)) &&
                (thisRule.toTimeType && (thisRule.toTimeType != timeTime() || thisRule.toTime)))    {
                def fTime = ( thisRule.fromTimeType == timeSunrise() ? sunriseTime : ( thisRule.fromTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.fromTime, location.timeZone)))
                def tTime = ( thisRule.toTimeType == timeSunrise() ? sunriseTime : ( thisRule.toTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.toTime, location.timeZone)))
//                ifDebug("ruleNo: $ruleNo | fTime: $fTime | tTime: $tTime | nowDate: $nowDate | timeOfDayIsBetween: ${timeOfDayIsBetween(fTime, tTime, nowDate, location.timeZone)}")
                if (!(timeOfDayIsBetween(fTime, tTime, nowDate, location.timeZone)))    continue;
            }
//            ifDebug("ruleNo: $thisRule.ruleNo | thisRule.luxThreshold: $thisRule.luxThreshold | turnOn: $turnOn | previousRuleLux: $previousRuleLux")
            if (thisRule.luxThreshold != null)      {
                if (!turnOn || !previousRuleLux || thisRule.luxThreshold < previousRuleLux)    {
                    turnOn = ruleNo
                    previousRuleLux = thisRule.luxThreshold
                }
            }
            else    {
                if (!turnOn)
                    turnOn = ruleNo
//                break
            }
        }
    }
    if (turnOn)     {
        ifDebug("${app.label} executed rule no: $turnOn")
        thisRule = getRule(turnOn)
        switchesOnOff(thisRule)
        runActions(thisRule)
        executePiston(thisRule)
        musicAction(thisRule)
        return true
    }
    else
        return false
}

private switchesOnOff(thisRule)       {
    ifDebug("switchesOnOff")
    if (thisRule && (thisRule.switchesOn || thisRule.switchesOff))
        state.previousRuleNo = thisRule.ruleNo
    if (thisRule.switchesOn)    {
        def colorTemperature = null
        def level = null
        thisRule.switchesOn.each      {
            if (it.currentSwitch != 'on')
                it.on();
            def itID = it.getId()
            if (thisRule.color && state.switchesHasColor[itID])     {
                if (it.currentColor != thisRule.hue)
                    it.setColor(thisRule.hue);
            }
            else
                if ((thisRule.colorTemperature || (thisRule.level == 'AL' && autoColorTemperature)) && state.switchesHasColorTemperature[itID])       {
                    if (!colorTemperature)      {
                        if (thisRule.level == 'AL' && autoColorTemperature)
                            colorTemperature = calculateLevelOrKelvin(true) as Integer
                        else
                            colorTemperature = thisRule.colorTemperature as Integer
                    }
                    if (it.currentColorTemperature != colorTemperature)
                        it.setColorTemperature(colorTemperature)
                }
            if (thisRule.level && state.switchesHasLevel[itID])     {
                if (!level)     {
                    if (thisRule.level == 'AL')
                        level = calculateLightLevel() as Integer
                    else
                        level = thisRule.level as Integer
                }
                if (it.currentLevel != level)
                    it.setLevel(level)
            }
        }
        def child = getChildDevice(getRoom())
        child.updateSwitchInd(1)
    }
    if (thisRule.switchesOff)
        if (thisRule.switchesOff.currentSwitch.contains('on'))
            thisRule.switchesOff.off()
}

private runActions(thisRule)    {
    if (thisRule.actions)   {  thisRule.actions.each  {  location.helloHome?.execute(it)  }  }
}

private executePiston(thisRule)    {  if (thisRule.piston)  webCoRE_execute(thisRule.piston)  }

private musicAction(thisRule)       {
    if (musicDevice && thisRule.musicAction)
        if (thisRule.musicAction == '1')    {
            musicDevice.play()
// to unmute or not?            musicDevice.unmute()
        }
        else
            if (thisRule.musicAction == '2')
                musicDevice.pause()
}

private calculateLevelOrKelvin(kelvin = false)       {
    ifDebug("calculateLevelOrKelvin")
    long timeNow = now()
    def dateNow = new Date(timeNow)

    def wTime = timeTodayAfter(sleepTime, wakeupTime, location.timeZone)
    def sTime = timeToday(sleepTime, location.timeZone)
//    if (wTime > sTime)      return maxKelvin;

    if (kelvin)     {
        if (state.kelvin && state.kelvin.kelvin && dateNow.getTime() < state.kelvin.time && wTime == state.kelvin.wTime && sTime == state.kelvin.sTime &&
           minKelvin == state.kelvin.minKelvin && maxKelvin == state.kelvin.maxKelvin)
           return state.kelvin.kelvin
        else
            state.kelvin = [time: (dateNow.getTime() + 600000L), wTime: wTime, sTime: sTime, minKelvin: minKelvin, maxKelvin: maxKelvin]
    }
    else        {
        if (state.level && state.level.level && dateNow.getTime() < state.level.time && wTime == state.level.wTime && sTime == state.level.sTime &&
           minLevel == state.level.minLevel && maxLevel == state.level.maxLevel)
           return state.level.level
        else
            state.level = [time: (dateNow.getTime() + 600000L), wTime: wTime, sTime: sTime, minLevel: minLevel, maxLevel: maxLevel]
    }

    def wTimeMinus1hr = new Date((wTime.getTime() - 3600000L))
    def sTimeMinus2hr = new Date((sTime.getTime() - 7200000L))

//    ifDebug("now: $dateNow | wTimeMinus1hr: $wTimeMinus1hr | sTimeMinus2hr: $sTimeMinus2hr")

    if (timeOfDayIsBetween(sTimeMinus2hr, wTimeMinus1hr, dateNow, location.timeZone))     {
        ifDebug("in sleep hours")
        if (kelvin)     {
            state.kelvin << [kelvin: minKelvin]
            return minKelvin
        }
        else        {
            state.level << [level: minLevel]
            return minLevel
        }
    }

    wTime = timeToday(wakeupTime, location.timeZone)
    wTimeMinus1hr = new Date((wTime.getTime() - 3600000L))

    def z1 = dateNow.format("HH", location.timeZone) as Integer
    def z2 = dateNow.format("mm", location.timeZone) as Integer
    long timeIs = (z1 * 3600L) + (z2 * 60L)

/*    def sunriseAndSunset = getSunriseAndSunset()
//    ifDebug("${new Date(sunriseAndSunset.sunrise.getTime())} | ${new Date(sunriseAndSunset.sunset.getTime())}")
    def sunriseTime = sunriseAndSunset.sunrise.getTime()
    def sunsetTime = sunriseAndSunset.sunset.getTime()
    long sunDiff = ((sunsetTime - sunriseTime) / 2L)
    def d = new Date(sunriseTime + sunDiff) */

//    ifDebug("now: $dateNow | wTimeMinus1hr: $wTimeMinus1hr | sTimeMinus2hr: $sTimeMinus2hr")

    long sunDiff = (sTimeMinus2hr.getTime() - wTimeMinus1hr.getTime()) / 2L
    def d = new Date(wTimeMinus1hr.getTime() + sunDiff)

    def peakMinus1hr = new Date((d.getTime() - 3600000L))
    def peakPlus1hr = new Date((d.getTime() + 3600000L))

//    ifDebug("now: $dateNow | peakMinus1hr: $peakMinus1hr | peakPlus1hr: $peakPlus1hr")

    if (timeOfDayIsBetween(peakMinus1hr, peakPlus1hr, dateNow, location.timeZone))     {
        ifDebug("in peak hours")
        if (kelvin)     {
            state.kelvin << [kelvin: maxKelvin]
            return maxKelvin
        }
        else        {
            state.level << [level: maxLevel]
            return maxLevel
        }
    }

    long maxMinDiff = (kelvin ? (maxKelvin - minKelvin) : (maxLevel - minLevel))
    double cDD
    int cD
    long timeStart
    long timeEnd
    if (dateNow < peakMinus1hr)     {
        z1 = wTimeMinus1hr.format("HH", location.timeZone) as Integer
        z2 = wTimeMinus1hr.format("mm", location.timeZone) as Integer
        timeStart = (z1 * 3600L) + (z2 * 60L)

        z1 = peakMinus1hr.format("HH", location.timeZone) as Integer
        z2 = peakMinus1hr.format("mm", location.timeZone) as Integer
        timeEnd = (z1 * 3600L) + (z2 * 60L)

        cDD = (timeIs - timeStart) / (timeEnd - timeStart)
        cDD = cDD * maxMinDiff
    }
    else        {
        z1 = peakPlus1hr.format("HH", location.timeZone) as Integer
        z2 = peakPlus1hr.format("mm", location.timeZone) as Integer
        timeStart = (z1 * 3600L) + (z2 * 60L)

        z1 = sTimeMinus2hr.format("HH", location.timeZone) as Integer
        z2 = sTimeMinus2hr.format("mm", location.timeZone) as Integer
        timeEnd = (z1 * 3600L) + (z2 * 60L)

        cDD = (timeIs - timeStart) / (timeEnd - timeStart)
        cDD = cDD * maxMinDiff
        cDD = maxMinDiff - cDD
    }

//    ifDebug("timeStart: $timeStart | timeEnd: $timeEnd | timeIs: $timeIs")

    if (kelvin)     {
        cD = cDD + minKelvin
        state.kelvin << [kelvin: cD]
    }
    else        {
        cD = cDD + minLevel
        state.level << [level: cD]
    }
    ifDebug("circadian Daylight ${(kelvin ? 'kelvin' : 'level')}: $cD")
    return cD
}

private calculateLightLevel()       {
    ifDebug("calculateLightLevel")
    if (autoColorTemperature)
        return calculateLevelOrKelvin(false)

    long timeNow = now()
    def d = new Date(timeNow)
    def z11 = d.format("HH", location.timeZone) as Integer
    def z12 = d.format("mm", location.timeZone) as Integer
    long timeIs = (z11 * 3600L) + (z12 * 60L)
    def sunriseAndSunset = getSunriseAndSunset()
//    ifDebug("${new Date(sunriseAndSunset.sunrise.getTime())} | ${new Date(sunriseAndSunset.sunset.getTime())}")
    def sunriseTime = sunriseAndSunset.sunrise.getTime()
    def sunsetTime = sunriseAndSunset.sunset.getTime()
    long sunDiff = ((sunsetTime - sunriseTime) / 2L)
    d = new Date(sunriseTime + sunDiff)
//    ifDebug("peakTime: $d")
    def z21 = d.format("HH", location.timeZone) as Integer
    def z22 = d.format("mm", location.timeZone) as Integer
    long time1500 = (z21 * 3600L) + (z22 * 60L)
    d = timeToday("23:59", location.timeZone)
    def z31 = d.format("HH", location.timeZone) as Integer
    def z32 = d.format("mm", location.timeZone) as Integer
    long time2359 = (z31 * 3600L) + (z32 * 60L)
//    ifDebug("$z11, $z12, $z21, $z22, $z31, $z32")

    long levelDiff = (maxLevel - minLevel)
    double lLD
    int lD
    if (timeIs < time1500)  {
        lLD = timeIs / time1500
        lLD = lLD * levelDiff
    }
    else    {
        lLD = timeIs / time2359
        lLD = lLD * levelDiff
        lLD = levelDiff - lLD
    }
    lD = lLD + minLevel
    ifDebug("Daylight level: $lD")
    return lD
}

private whichSwitchesAreOn()   {
    ifDebug("whichSwitchesAreOn")
    def switchesThatAreOn = []
    def switchesThatAreOnID = []
    for (def i = 1; i < 11; i++)      {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (thisRule && !thisRule.disabled && thisRule.switchesOn)      {
            thisRule.switchesOn.each        {
                def itID = it.getId()
                if (it.currentSwitch == 'on' && !(switchesThatAreOnID.contains(itID)))    {
                    switchesThatAreOn << it
                    switchesThatAreOnID << itID
                }
            }
        }
    }
    return switchesThatAreOn
}

def dimLights()     {
    ifDebug("dim lights")
    state.preDimLevel = [:]
    if (!state.dimTimer || !state.dimByLevel)       return;
//    if (!state.dimTimer || !state.dimByLevel || !state.previousRuleNo)      return;
//    def thisRule = getRule(state.previousRuleNo)
//    if (!thisRule.switchesOn)       return;
    def switchesThatAreOn = whichSwitchesAreOn()
    switchesThatAreOn.each      {
        if (it.currentValue("switch") == 'on')      {
            if (it.hasCommand("setLevel"))     {
                def currentLevel = it.currentValue("level")
                def newLevel = (currentLevel > state.dimByLevel ? currentLevel - state.dimByLevel : 1)
                it.setLevel(newLevel)
                state.preDimLevel << [(it.getId()):currentLevel]
            }
        }
    }

/*    if (state.dimTimer)       {
        if (state.dimByLevel)      {
            switches2.each      {
                if (it.currentValue("switch") == 'on')       {
                    if (state.switchesHasLevel[it.getId()])     {
                        def currentLevel = it.currentValue("level")
                        def newLevel = (currentLevel > state.dimByLevel ? currentLevel - state.dimByLevel : 1)
                        it.setLevel(newLevel)
                    }
                }
            }
            if (allSwitches && allSwitchesOff)  {
                switches.each   {
                    if (it.currentValue("switch") == 'on')       {
                        if (state.switchesHasLevel[it.getId()])     {
                            def currentLevel = it.currentValue("level")
                            def newLevel = (currentLevel > state.dimByLevel ? currentLevel - state.dimByLevel : 1)
                            it.setLevel(newLevel)
                        }
                    }
                }
            }
        }
    }
    runIn(state.dimTimer ?: 1, switches2Off, [data: [allSwitches: allSwitches]])*/
}

//def forceSwitches2Off()     {  switches2Off(allSwitchesOff)  }

def unDimLights()       {
    ifDebug("unDimLights")
    ifDebug("state.preDimLevel: $state.preDimLevel")
//    if (!dimTimer || !dimByLevel || !state.previousRuleNo || !state.preDimLevel)      return;
    if (!dimTimer || !dimByLevel || !state.preDimLevel)      return;
//    def thisRule = getRule(state.previousRuleNo)
//    if (thisRule.level)         return;
    def switchesThatAreOn = whichSwitchesAreOn()
    ifDebug("undim switchesThatAreOn: $switchesThatAreOn")
    switchesThatAreOn.each      {
        if (it.currentValue("switch") == 'on')      {
            if (it.hasCommand("setLevel"))     {
                def newLevel = state.preDimLevel[(it.getId())]
                ifDebug("newLevel: $newLevel")
                if (newLevel > 0)
                    it.setLevel(newLevel)
            }
        }
    }
    updateChildTimer(0)
    state.preDimLevel = [:]
}

def switches2Off()       {
    ifDebug("switches2Off")
/*    if (state.previousRuleNo)       {
        def thisRule = getRule(state.previousRuleNo)
        if (thisRule && thisRule.switchesOn)
            thisRule.switchesOn.off();
        def child = getChildDevice(getRoom())
        child.updateSwitchInd(isAnySwitchOn())
    }*/
    def switchesThatAreOn = whichSwitchesAreOn()
    switchesThatAreOn.each      {
        if (it.currentSwitch != 'off')
            it.off()
    }
/*    def i = 1
    for (; i < 11; i++)      {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (thisRule && !thisRule.disabled && thisRule.switchesOn)      {
            thisRule.switchesOn.each        {
                it.off()
            }
        }
    }*/
}

private previousStateStack(previousState)    {
    def i
    def timeIs = now()
    def removeHowOld = (state.noMotion ? ((state.noMotion + state.dimTimer) * 10) : (180 * 10))
    def howMany
    int gapBetween

    turnOffIsBusy()
    if (state.stateStack)       {
        for (i = 9; i > 0; i--)     {
            def s = String.valueOf(i)
            if (state.stateStack[s])        {
                gapBetween = ((timeIs - (state.stateStack[s])['date']) / 1000)
                if (gapBetween > removeHowOld)
                    state.stateStack.remove(s)
                else
                    break
            }
        }
    }
    if (state.stateStack)       {
        for (i = 9; i > 0; i--)     {
            if (state.stateStack[String.valueOf(i-1)])
                state.stateStack[String.valueOf(i)] = state.stateStack[String.valueOf(i-1)]
        }
    }
    else
        state.stateStack = [:]
    state.stateStack << ['0':previousState]

    if (state.busyCheck)      {

/*        howMany = 0
        gapBetween = 0
        state.stateStack.each   { k, v ->
            if (['occupied', 'checking', 'vacant'].contains(v['state']))     {
                howMany++
                gapBetween += ((timeIs - v['date']) / 1000)
            }
        }
        ifDebug("howMany: $howMany | gapBetween: $gapBetween")*/

        howMany = 0
        gapBetween = 0
        for (i = 9; i > 0; i--)     {
            def s = String.valueOf(i)
            def sM = String.valueOf(i-1)
            if (state.stateStack[s] && ['occupied', 'vacant'].contains((state.stateStack[s])['state']) &&
                                       ['occupied', 'vacant'].contains((state.stateStack[sM])['state']))         {
                howMany++
                gapBetween += (((state.stateStack[sM])['date'] - (state.stateStack[s])['date']) / 1000)
            }
        }
        ifDebug("howMany: $howMany | gapBetween: $gapBetween | busyCheck: $state.busyCheck | isBusy: $state.isBusy | newState: $newState")
        if (howMany >= state.busyCheck)   {
            ifDebug("busy on")
            state.isBusy = true
            state.stateStack = [:]
            runIn(removeHowOld, turnOffIsBusy)
        }
    }
}

def turnOffIsBusy()     {
    unschedule('turnOffIsBusy')
    state.isBusy = false
}

def spawnChildDevice(roomName)	{
	app.updateLabel(app.label)
	if (!childCreated())
		def child = addChildDevice("bangali", "rooms occupancy", getRoom(), null, [name: getRoom(), label: roomName, completedSetup: true])
}

private childCreated()		{
	if (getChildDevice(getRoom()))
		return true
	else
		return false
}

private getRoom()	{	return "rm_${app.id}"	}

def uninstalled() {
	getChildDevices().each	{
		deleteChildDevice(it.deviceNetworkId)
	}
}

def childUninstalled()  {
ifDebug("uninstalled room ${app.label}")
}

private saveHueToState()        {
    if (setColorTo)     {
        def hueColor = 0
        def saturation = 100
        switch(setColorTo)       {
            case "White":       hueColor = 52;  saturation = 19;    break;
            case "Daylight":    hueColor = 53;  saturation = 91;    break;
            case "Soft White":  hueColor = 23;  saturation = 56;    break;
            case "Warm White":  hueColor = 20;  saturation = 80;    break;
            case "Blue":        hueColor = 70;                      break;
            case "Green":       hueColor = 39;                      break;
            case "Yellow":      hueColor = 25;                      break;
            case "Orange":      hueColor = 10;                      break;
            case "Purple":      hueColor = 75;                      break;
            case "Pink":        hueColor = 83;                      break;
            case "Red":         hueColor = 100;                     break;
        }
        state.setColorTo = [hue: hueColor, saturation: saturation]
    }
    else
        state.setColorTo = null
}

private returnHueAndSaturation(setColorTo)        {
    def rHAS
    if (setColorTo)     {
        def hueColor = 0
        def saturation = 100
        switch(setColorTo)       {
            case "White":       hueColor = 52;  saturation = 19;    break;
            case "Daylight":    hueColor = 53;  saturation = 91;    break;
            case "Soft White":  hueColor = 23;  saturation = 56;    break;
            case "Warm White":  hueColor = 20;  saturation = 80;    break;
            case "Blue":        hueColor = 66;                      break;
            case "Green":       hueColor = 39;                      break;
            case "Yellow":      hueColor = 25;                      break;
            case "Orange":      hueColor = 10;                      break;
            case "Purple":      hueColor = 75;                      break;
            case "Pink":        hueColor = 83;                      break;
            case "Red":         hueColor = 0;                       break;
        }
        rHAS = [hue: hueColor, saturation: saturation]
    }
    else
        rHAS = null
    return rHAS
}

private unscheduleAll(classNameCalledFrom)		{
    ifDebug("${app.label} unschedule calling class: $classNameCalledFrom")
    unschedule('roomVacant')
    unschedule('setToEngaged')
    updateChildTimer(0)
//    unschedule("dimLights")
//    unschedule("switches2Off")
}

private updateChildTimer(timer = 0)	{
	state.timer = (timer ? timer + 5 : 0)
	timerNext()
}

private updateAsleepChildTimer(timer = 0)	{
//	state.timer = (timer ? timer + 5 : 0)
//	timerNext()
}

def timerNext()		{
	def toRunIn = 5
	if (state.timer >= 5)
		state.timer = state.timer - 5
	else	{
		toRunIn = state.timer
		state.timer = 0
	}
    def child = getChildDevice(getRoom())
    child.updateTimer(state.timer)
	if (toRunIn)
		runIn(toRunIn, timerNext)
    else
        unschedule('timerNext')
}

/*
private scheduleFromToTimes()       {
    if (!state.rules || !state.timeCheck)
        return
    def sunriseFromSubscribed = false
    def sunriseToSubscribed = false
    def sunsetFromSubscribed = false
    def sunsetToSubscribed = false
    for (def rule in state.rules.sort{ it.key })        {
        def thisRule = rule.value
        if (thisRule.disabled)      continue
        if (!thisRule.fromTimeType || !thisRule.toTimeType)     continue
        ifDebug("$thisRule")
        if (thisRule.fromTimeType == timeTime())
            schedule(thisRule.fromTime, timeFromHandler, [overwrite: false])
        else    {
            if (thisRule.fromTimeType == timeSunrise() && !sunriseFromSubscribed)   {
                subscribe(location, "sunrise", timeFromHandler)
                sunriseFromSubscribed = true
            }
            else
                if (!sunsetFromSubscribed)   {
                    subscribe(location, "sunset", timeFromHandler)
                    sunsetFromSubscribed = true
                }
        }
        if (thisRule.toTimeType == timeTime())
            schedule(thisRule.toTime, timeToHandler, [overwrite: false])
        else    {
            if (thisRule.toTimeType == timeSunrise() && !sunriseToSubscribed)   {
                subscribe(location, "sunrise", timeToHandler)
                sunriseToSubscribed = true
            }
            else
                if (!sunsetToSubscribed)   {
                    subscribe(location, "sunset", timeToHandler)
                    sunsetToSubscribed = true
                }
        }
    }
}
*/

def scheduleFromToTimes()       {
    if (!state.rules || !state.timeCheck)
        return
    ifDebug("scheduleFromToTimes")
    def sunriseFromSubscribed = false
    def sunriseToSubscribed = false
    def sunsetFromSubscribed = false
    def sunsetToSubscribed = false
/*    for (def rule in state.rules.sort{ it.key })        {
        def thisRule = rule.value
        if (thisRule.disabled)      continue
        if (!thisRule.fromTimeType || !thisRule.toTimeType)     continue
        ifDebug("$thisRule")
        if (thisRule.fromTimeType == timeSunrise() && !sunriseFromSubscribed)   {
            subscribe(location, "sunrise", timeFromHandler)
            sunriseFromSubscribed = true
        }
        else
            if (thisRule.fromTimeType == timeSunset() && !sunsetFromSubscribed)   {
                subscribe(location, "sunset", timeFromHandler)
                sunsetFromSubscribed = true
            }
        if (thisRule.toTimeType == timeSunrise() && !sunriseToSubscribed)   {
            subscribe(location, "sunrise", timeToHandler)
            sunriseToSubscribed = true
        }
        else
            if (thisRule.toTimeType == timeSunset() && !sunsetToSubscribed)   {
                subscribe(location, "sunset", timeToHandler)
                sunsetToSubscribed = true
            }
    }*/
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (!thisRule || thisRule.disabled)      continue
        if (!thisRule.fromTimeType || !thisRule.toTimeType)     continue
        if (thisRule.fromTimeType == timeSunrise() && !sunriseFromSubscribed)   {
            subscribe(location, "sunrise", timeFromHandler)
            sunriseFromSubscribed = true
        }
        else    {
            if (thisRule.fromTimeType == timeSunset() && !sunsetFromSubscribed)   {
                subscribe(location, "sunset", timeFromHandler)
                sunsetFromSubscribed = true
            }
        }
        if (thisRule.toTimeType == timeSunrise() && !sunriseToSubscribed)   {
            subscribe(location, "sunrise", timeToHandler)
            sunriseToSubscribed = true
        }
        else    {
            if (thisRule.toTimeType == timeSunset() && !sunsetToSubscribed)   {
                subscribe(location, "sunset", timeToHandler)
                sunsetToSubscribed = true
            }
        }
    }
    scheduleFromTime()
    scheduleToTime()
}

private scheduleFromTime()      {
    if (!state.rules || !state.timeCheck)       return;
    ifDebug("scheduleFromTime")
    def nowTime	= now()
    def nowDate = new Date(nowTime)
    def sunriseAndSunset = getSunriseAndSunset()
    def sunriseTime = new Date(sunriseAndSunset.sunrise.getTime())
    def sunsetTime = new Date(sunriseAndSunset.sunset.getTime())
    def nextTimeType = null
    def nextTime = null
/*    for (def rule in state.rules.sort{ it.key })        {
        def thisRule = rule.value
        if (thisRule.disabled)      continue
        if (!thisRule.fromTimeType || thisRule.fromTimeType != timeTime() || !thisRule.fromTime || !thisRule.toTimeType)
            continue
        def fromTime = timeTodayAfter(nowDate, thisRule.fromTime, location.timeZone)
        if (!nextTime)      {
            nextTime = fromTime
            continue
        }
        ifDebug("nowDate: $nowDate || nextTime: $nextTime || fromTime: $fromTime")
        ifDebug("${timeOfDayIsBetween(nowDate, nextTime, fromTime, location.timeZone)}")
        if (timeOfDayIsBetween(nowDate, nextTime, fromTime, location.timeZone))
            nextTime = fromTime
    }*/
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (!thisRule || thisRule.disabled)      continue;
        if (thisRule.fromDate && thisRule.toDate)       {
//            ifDebug("scheduleFromTime: thisRule.fromDate: $thisRule.fromDate | thisRule.toDate: $thisRule.toDate")
            def fTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.fromDate)
            def tTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.toDate)
//            ifDebug("scheduleFromTime: nowDate: $nowDate | nextTime: $nextTime | fTime: $fTime | tTime: $tTime")
            if (nowDate > tTime)        continue;
//            if ((!nextTime && nowDate < fTime) || (nextTime && timeOfDayIsBetween(nowDate, nextTime, fTime, location.timeZone)))   {
            if ((!nextTime && nowDate >= fTime && nowDate <= tTime) || (nextTime && fTime >= nowDate && fTime < nextTime))   {
                nextTime = fTime
                nextTimeType = timeTime()
            }
        }
        if ((!thisRule.fromTimeType || (thisRule.fromTimeType == timeTime() && !thisRule.fromTime)) ||
            (!thisRule.toTimeType && (thisRule.toTimeType != timeTime() || thisRule.toTime)))
            continue
//        def fromTime = timeTodayAfter(nowDate, thisRule.fromTime, location.timeZone)
        def fTime = ( thisRule.fromTimeType == timeSunrise() ? sunriseTime : ( thisRule.fromTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.fromTime, location.timeZone)))
//        ifDebug("scheduleFromTime 2: nowDate: $nowDate | nextTime: $nextTime | fTime: $fTime | tTime: $tTime")
//        if (!nextTime || nowDate > nextTime || timeOfDayIsBetween(nowDate, nextTime, fTime, location.timeZone))      {
        if (!nextTime || timeOfDayIsBetween(nowDate, nextTime, fTime, location.timeZone))      {
            nextTimeType = thisRule.fromTimeType
            nextTime = fTime
//            continue
        }
//        if (timeOfDayIsBetween(nowDate, nextTime, fTime, location.timeZone)) {
//            nextTimeType = thisRule.fromTimeType
//            nextTime = fTime
//        }
    }
    if (nextTime)   {
        state.fTime = nextTime
        updateTimeFromToInd()
        if (nextTimeType == timeTime())
            schedule(nextTime, timeFromHandler)
    }
}

private scheduleToTime()      {
    if (!state.rules || !state.timeCheck)       return;
    ifDebug("scheduleToTime")
    def nowTime	= now()
    def nowDate = new Date(nowTime)
    def sunriseAndSunset = getSunriseAndSunset()
    def sunriseTime = new Date(sunriseAndSunset.sunrise.getTime())
    def sunsetTime = new Date(sunriseAndSunset.sunset.getTime())
    def nextTimeType = null
    def nextTime = null
/*    for (def rule in state.rules.sort{ it.key })        {
        def thisRule = rule.value
        if (thisRule.disabled)      continue
        if (!thisRule.toTimeType || thisRule.toTimeType != timeTime() || !thisRule.toTime || !thisRule.fromTimeType)
            continue
        def toTime = timeTodayAfter(nowDate, thisRule.toTime, location.timeZone)
        if (!nextTime)      {
            nextTime = toTime
            continue
        }
        ifDebug("nowDate: $nowDate || nextTime: $nextTime || toTime: $toTime")
        ifDebug("${timeOfDayIsBetween(nowDate, nextTime, toTime, location.timeZone)}")
        if (timeOfDayIsBetween(nowDate, nextTime, toTime, location.timeZone))
            nextTime = toTime
    }*/
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (!thisRule || thisRule.disabled)      continue;
/*        if (thisRule.fromDate && thisRule.toDate)       {
            def fTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.fromDate)
            def tTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.toDate)
            if (nowDate > tTime)        continue;
            if (!nextTime || timeOfDayIsBetween(nowDate, nextTime, tTime, location.timeZone))   {
                nextTime = tTime
                nextTimeType = timeTime()
            }
        }*/
        if (thisRule.fromDate && thisRule.toDate)       {
//            ifDebug("scheduleToTime: thisRule.fromDate: $thisRule.fromDate | thisRule.toDate: $thisRule.toDate")
            def fTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.fromDate)
            def tTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssZ", thisRule.toDate)
//            ifDebug("scheduleToTime: nowDate: $nowDate | nextTime: $nextTime | fTime: $fTime | tTime: $tTime")
            if (nowDate > tTime)        continue;
//            if ((!nextTime && nowDate < tTime) || (nextTime && timeOfDayIsBetween(nowDate, nextTime, tTime, location.timeZone)))   {
            if ((!nextTime && nowDate >= fTime && nowDate <= tTime) || (nextTime && tTime >= nowDate && tTime < nextTime))   {
                nextTime = tTime
                nextTimeType = timeTime()
            }
        }
        if ((!thisRule.fromTimeType || (thisRule.fromTimeType == timeTime() && !thisRule.fromTime)) ||
            (!thisRule.toTimeType && (thisRule.toTimeType != timeTime() || thisRule.toTime)))
            continue
//        def toTime = timeTodayAfter(nowDate, thisRule.toTime, location.timeZone)
        def tTime = ( thisRule.toTimeType == timeSunrise() ? sunriseTime : ( thisRule.toTimeType == timeSunset() ? sunsetTime : timeToday(thisRule.toTime, location.timeZone)))
        if (!nextTime || timeOfDayIsBetween(nowDate, nextTime, tTime, location.timeZone))      {
            nextTimeType = thisRule.toTimeType
            nextTime = tTime
//            continue
        }
//        if (timeOfDayIsBetween(nowDate, nextTime, tTime, location.timeZone))    {
//            nextTimeType = thisRule.toTimeType
//            nextTime = tTime
//        }
    }
    if (nextTime)   {
        state.tTime = nextTime
        updateTimeFromToInd()
        if (nextTimeType == timeTime())
            schedule(nextTime, timeToHandler)
    }
}

def timeFromHandler(evt = null)       {
    ifDebug("timeFromHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))       return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
//    def child = getChildDevice(getRoom())
//    def roomState = child.getRoomState()
//    if (['engaged', 'occupied', 'asleep', 'vacant'].contains(roomState))
        turnOnAndOffSwitches()
    scheduleFromToTimes()
}

def timeToHandler(evt = null)       {
    ifDebug("timeToHandler")
    if (pauseModes && pauseModes.contains(location.currentMode))       return;
    if (state.dayOfWeek && !(checkRunDay()))        return;
//    def child = getChildDevice(getRoom())
//    def roomState = child.getRoomState()
//    if (['engaged', 'occupied', 'asleep', 'vacant'].contains(roomState))
        turnOnAndOffSwitches()
    scheduleFromToTimes()
}

private updateTimeFromToInd()     {
    if (state.fTime && state.tTime)     {
        state.timeFromTo =  format24hrTime(state.fTime) + " "
        state.timeFromTo = state.timeFromTo + format24hrTime(state.tTime)
        def child = getChildDevice(getRoom())
        child.updateTimeInd(state.timeFromTo)
    }
}

private format24hrTime(timeToFormat = new Date(now()), format = "HH:mm")		{
    return timeToFormat.format("HH:mm", location.timeZone)
}

def getAdjMotionSensors()  {
    if (motionSensors)   {
        def motionSensorsList = []
        motionSensors.each   {  motionSensorsList << it }
        return motionSensorsList
    }
    else
        return null
}

def getAdjRoomDetails()  {
    def adjRoomDetails = ['childid':app.id, 'adjrooms':adjRooms]
ifDebug("childid: ${adjRoomDetails['childid']} | adjrooms: ${adjRoomDetails['adjrooms']}")
/*    if (motionSensors)   {
        def motionSensorsList = []
        def motionSensorsNameList = []
        motionSensors.each   {
            motionSensorsList << it
            motionsSensorName = it.getName()
        }
        adjRoomDetails << ['motionsensors':motionSensorsList]
        adjRoomDetails << ['motionsensorsnames':motionSensorsNamesList]
    }
    else
        adjRoomDetails << ['motionsensors':null,'motionsensorsnames':null]
*/
    return adjRoomDetails
}

def getLastStateChild()     {
    def addRoom = state.previousState
    addRoom << ['room':app.label]
    return addRoom
}

private checkRunDay(dayOfWeek = null)   {
    def thisDay = (new Date(now())).getDay()
    if (dayOfWeek)
        return (dayOfWeek.contains(thisDay))
    else
        return (state.dayOfWeek.contains(thisDay))
}

private lastMotionActive()      {  return '1'  }
private lastMotionInactive()    {  return '2'  }

private timeSunrise()   {  return '1'  }
private timeSunset()    {  return '2'  }
private timeTime()      {  return '3'  }

private presenceActionArrival()       {  return (presenceAction == '1' || presenceAction == '3')  }
private presenceActionDeparture()     {  return (presenceAction == '2' || presenceAction == '3')  }

private ifDebug(msg = null)     {  if (msg && isDebug()) log.debug msg  }

// only called from device handler
def turnSwitchesAllOnOrOff(turnOn)     {
    ifDebug("turnSwitchesAllOnOrOff")
    def switches = getAllSwitches()
    if (switches)   {
        if (turnOn)     switches.each   {  if (it.currentSwitch != 'on')   it.on()  }
        else            switches.each   {  if (it.currentSwitch != 'off')  it.off()  }
    }
}

private getAllSwitches()    {
    def switches = []
    def i = 1
    for (; i < 11; i++)     {
        def ruleNo = String.valueOf(i)
        def thisRule = getRule(ruleNo)
        if (thisRule && !thisRule.disabled && thisRule.switchesOn)
            thisRule.switchesOn.each        {
                if (!switches.contains(it))
                    switches << it
        }
    }
    ifDebug("getAllSwitches: $switches")
    return switches
}

//------------------------------------------------------Night option------------------------------------------------------//
def	nightButtonPushedEventHandler(evt)     {
    if (!evt.data)
        return
    def nM = new groovy.json.JsonSlurper().parseText(evt.data)
    assert nM instanceof Map
    if (!nM || (nightButtonIs && nM['buttonNumber'] != nightButtonIs as Integer))
        return
    def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (nightSwitches && roomState == 'asleep')     {
        unscheduleAll("night button pushed handler")
        def switchValue = nightSwitches.currentValue("switch")
        if (nightButtonAction == "1")
        {
        	ifDebug("action 1")
        	dimNightLights()
        }
        else if (nightButtonAction == "2" && switchValue.contains('on'))
        {
        	ifDebug("action 2")
        	nightSwitchesOff()
        }
        else if (nightButtonAction == "3")
        {
        	ifDebug("action 3")
        	if (switchValue.contains('on'))
            	nightSwitchesOff()
        	else
            	dimNightLights()
        }
    }
}

def dimNightLights()     {
    nightSwitches.each      {
        if (it.currentSwitch != 'on')
            it.on()
        if (state.nightSetLevelTo && state.switchesHasLevel[it.getId()])
            it.setLevel(state.nightSetLevelTo)
    }
}

def nightSwitchesOff()      {
//    unscheduleAll("night switches off")
    unschedule('nightSwitchesOff')
    if (nightSwitches)
        nightSwitches.off()
}

def sleepEventHandler(evt)		{
ifDebug("sleepEventHandler: ${asleepSensor} - ${evt.value}")
	def child = getChildDevice(getRoom())
    def roomState = child.getRoomState()
    if (evt.value == "not sleeping")
    	child.generateEvent('checking')
    else    {
        if (evt.value == "sleeping")
    	       child.generateEvent('asleep')
    }
}
//------------------------------------------------------------------------------------------------------------------------//

/*************************************************************************/
/* webCoRE Connector v0.2                                                */
/*************************************************************************/
/*  Copyright 2016 Adrian Caramaliu <ady624(at)gmail.com>                */
/*                                                                       */
/*  This program is free software: you can redistribute it and/or modify */
/*  it under the terms of the GNU General Public License as published by */
/*  the Free Software Foundation, either version 3 of the License, or    */
/*  (at your option) any later version.                                  */
/*                                                                       */
/*  This program is distributed in the hope that it will be useful,      */
/*  but WITHOUT ANY WARRANTY; without even the implied warranty of       */
/*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the         */
/*  GNU General Public License for more details.                         */
/*                                                                       */
/*  You should have received a copy of the GNU General Public License    */
/*  along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
/*************************************************************************/
/*  Initialize the connector in your initialize() method using           */
/*     webCoRE_init()                                                    */
/*  Optionally, pass the string name of a method to call when a piston   */
/*  is executed:                                                         */
/*     webCoRE_init('pistonExecutedMethod')                              */
/*************************************************************************/
/*  List all available pistons by using one of the following:            */
/*     webCoRE_list() - returns the list of id/name pairs                */
/*     webCoRE_list('id') - returns the list of piston IDs               */
/*     webCoRE_list('name') - returns the list of piston names           */
/*************************************************************************/
/*  Execute a piston by using the following:                             */
/*     webCoRE_execute(pistonIdOrName)                                   */
/*  The execute method accepts either an id or the name of a             */
/*  piston, previously retrieved by webCoRE_list()                       */
/*************************************************************************/
private webCoRE_handle(){return'webCoRE'}
private webCoRE_init(pistonExecutedCbk)
{
    state.webCoRE=(state.webCoRE instanceof Map?state.webCoRE:[:])+(pistonExecutedCbk?[cbk:pistonExecutedCbk]:[:]);
    subscribe(location,"${webCoRE_handle()}.pistonList",webCoRE_handler);
    if(pistonExecutedCbk)subscribe(location,"${webCoRE_handle()}.pistonExecuted",webCoRE_handler);webCoRE_poll();
}
private webCoRE_poll(){sendLocationEvent([name: webCoRE_handle(),value:'poll',isStateChange:true,displayed:false])}
public  webCoRE_execute(pistonIdOrName,Map data=[:]){def i=(state.webCoRE?.pistons?:[]).find{(it.name==pistonIdOrName)||(it.id==pistonIdOrName)}?.id;if(i){sendLocationEvent([name:i,value:app.label,isStateChange:true,displayed:false,data:data])}}
public  webCoRE_list(mode)
{
	def p=state.webCoRE?.pistons;
    if(p)p.collect{
		mode=='id'?it.id:(mode=='name'?it.name:[id:it.id,name:it.name])
        // log.debug "Reading piston: ${it}"
	}
    return p
}
public  webCoRE_handler(evt){switch(evt.value){case 'pistonList':List p=state.webCoRE?.pistons?:[];Map d=evt.jsonData?:[:];if(d.id&&d.pistons&&(d.pistons instanceof List)){p.removeAll{it.iid==d.id};p+=d.pistons.collect{[iid:d.id]+it}.sort{it.name};state.webCoRE = [updated:now(),pistons:p];};break;case 'pistonExecuted':def cbk=state.webCoRE?.cbk;if(cbk&&evt.jsonData)"$cbk"(evt.jsonData);break;}}
