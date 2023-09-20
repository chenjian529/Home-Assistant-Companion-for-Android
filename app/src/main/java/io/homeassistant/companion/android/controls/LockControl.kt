package io.homeassistant.companion.android.controls

import android.content.Context
import android.os.Build
import android.service.controls.Control
import android.service.controls.DeviceTypes
import android.service.controls.actions.BooleanAction
import android.service.controls.actions.ControlAction
import android.service.controls.templates.ControlButton
import android.service.controls.templates.ToggleTemplate
import androidx.annotation.RequiresApi
import io.homeassistant.companion.android.common.data.integration.Entity
import io.homeassistant.companion.android.common.data.integration.IntegrationRepository
import io.homeassistant.companion.android.common.data.integration.isActive
import io.homeassistant.companion.android.common.data.websocket.impl.entities.AreaRegistryResponse
import io.homeassistant.companion.android.common.R as commonR

@RequiresApi(Build.VERSION_CODES.R)
object LockControl : HaControl {
    override fun provideControlFeatures(
        context: Context,
        control: Control.StatefulBuilder,
        entity: Entity<Map<String, Any>>,
        area: AreaRegistryResponse?,
        baseUrl: String?
    ): Control.StatefulBuilder {
        control.setControlTemplate(
            ToggleTemplate(
                entity.entityId,
                ControlButton(
                    entity.isActive(),
                    "Description"
                )
            )
        )
        return control
    }

    override fun getDeviceType(entity: Entity<Map<String, Any>>): Int =
        DeviceTypes.TYPE_LOCK

    override fun getDomainString(context: Context, entity: Entity<Map<String, Any>>): String =
        context.getString(commonR.string.domain_lock)

    override suspend fun performAction(
        integrationRepository: IntegrationRepository,
        action: ControlAction
    ): Boolean {
        integrationRepository.callService(
            action.templateId.split(".")[0],
            if ((action as? BooleanAction)?.newState == true) "lock" else "unlock",
            hashMapOf("entity_id" to action.templateId)
        )
        return true
    }
}
